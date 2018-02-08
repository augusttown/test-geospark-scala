package local.spark.test

import org.apache.log4j.{Level, Logger}
//import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
//import org.apache.spark.sql.functions._
//import org.apache.spark.sql.Encoder

object TestBasicSpark {

    // Create the Spark Session and the spark context
    val spark = SparkSession
        .builder
        .appName(getClass.getSimpleName).master("local[*]")
        .getOrCreate()

    val sc = spark.sparkContext

    var inputDir = System.getProperty("user.dir")+"/src/test/resources/"
    var outputDir = System.getProperty("user.dir")+"/data/"
    var hdfsOutputDir = "hdfs://localhost:9000/user/yingqitang/test/"

    Logger.getLogger("org").setLevel(Level.WARN)

    // Define the case classes for using in conjunction with DataFrames and Dataset
    case class Trans(accNo: String, tranAmount: Double)
    import spark.implicits._

    def main(args: Array[String]) {

        //testBasicSparkRDD()
        //testBasicSparkDataFrameAPI()
        testBasicSparkDataset()
        //testBasicSparkDatacatalog()

    }

    def testBasicSparkRDD(): Unit = {
        val acTransList = Array("SB10001,1000", "SB10002,1200", "SB10003,8000", "SB10004,400", "SB10005,300", "SB10006,10000", "SB10007,500", "SB10008,56", "SB10009,30","SB10010,7000", "CR10001,7000", "SB10002,-10")
        val acTransRDD = sc.parallelize(acTransList)
        acTransRDD.collect().foreach(println)
    }

    def testBasicSparkDataFrameAPI(): Unit = {

    }

    def testBasicSparkDataset(): Unit = {

        // Creation of the list from where the Dataset is going to be created using a case class.
        val acTransList = Seq(Trans("SB10001", 1000), Trans("SB10002",1200), Trans("SB10003", 8000), Trans("SB10004",400), Trans("SB10005",300), Trans("SB10006",10000), Trans("SB10007",500), Trans("SB10008",56), Trans("SB10009",30),Trans("SB10010",7000), Trans("CR10001",7000), Trans("SB10002",-10))
        // Create the Dataset
        val acTransDS = acTransList.toDS()
        acTransDS.show()
        // Apply filter and create another Dataset of good transaction records
        val goodTransRecords = acTransDS.filter(_.tranAmount > 0).filter(_.accNo.startsWith("SB"))
        goodTransRecords.show()
        // Apply filter and create another Dataset of high value transaction records
        val highValueTransRecords = goodTransRecords.filter(_.tranAmount > 1000)
        highValueTransRecords.show()
        // The function that identifies the bad amounts
        val badAmountLambda = (trans: Trans) => trans.tranAmount <= 0
        // The function that identifies bad accounts
        val badAcNoLambda = (trans: Trans) => trans.accNo.startsWith("SB") == false
        // Apply filter and create another Dataset of bad amount records
        val badAmountRecords = acTransDS.filter(badAmountLambda)
        badAmountRecords.show()
        // Apply filter and create another Dataset of bad account records
        val badAccountRecords = acTransDS.filter(badAcNoLambda)
        badAccountRecords.show()
        // Do the union of two Dataset and create another Dataset
        val badTransRecords  = badAmountRecords.union(badAccountRecords)
        badTransRecords.show()
        // Calculate the sum
        val sumAmount = goodTransRecords.map(trans => trans.tranAmount).reduce(_ + _)
        // Calculate the maximum
        val maxAmount = goodTransRecords.map(trans => trans.tranAmount).reduce((a, b) => if (a > b) a else b)
        // Calculate the minimum
        val minAmount = goodTransRecords.map(trans => trans.tranAmount).reduce((a, b) => if (a < b) a else b)
        // Convert the Dataset to DataFrame
        val acTransDF = acTransDS.toDF()
        acTransDF.show()
        // Use Spark SQL to find out invalid transaction records
        acTransDF.createOrReplaceTempView("trans")
        val invalidTransactions = spark.sql("SELECT accNo, tranAmount FROM trans WHERE (accNo NOT LIKE 'SB%') OR tranAmount <= 0")
        invalidTransactions.show()
        // Interoperability of RDD, DataFrame and Dataset
        // Create RDD
        val acTransRDD = sc.parallelize(acTransList)
        // Convert RDD to DataFrame
        val acTransRDDtoDF = acTransRDD.toDF()
        acTransRDDtoDF.show()

        // Persist the data of the DataFrame into a Parquet file
        //acTransRDDtoDF.write.parquet(outputDir + "scala.trans.parquet")
        //acTransRDDtoDF.write.parquet(hdfsOutputDir + "scala.trans.parquet")

        // Convert the DataFrame to Dataset with the type checking
        val acTransDFtoDS = acTransRDDtoDF.as[Trans]

        val acTransDFfromParquet = spark.read.parquet(outputDir + "scala.trans.parquet")
        //System.out.println("read from hdfs parquet to DataFrame")
        //val acTransDFfromParquet = spark.read.parquet(hdfsOutputDir + "scala.trans.parquet")
        acTransDFfromParquet.show()
    }

    def testBasicSparkDatacatalog(): Unit = {

        val catalog = spark.catalog
        val dbList = catalog.listDatabases()

        dbList.select("name", "description", "locationUri").show()

        val tableList = catalog.listTables()
        tableList.show()

        //catalog.dropTempView("trans")

    }


}
