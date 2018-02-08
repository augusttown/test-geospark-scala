package local.spark.test

import com.vividsolutions.jts.geom.{Coordinate, Envelope, GeometryFactory}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.datasyslab.geospark.enums.{FileDataSplitter, GridType, IndexType}
import org.datasyslab.geospark.serde.GeoSparkKryoRegistrator
import org.datasyslab.geospark.spatialOperator.RangeQuery
import org.datasyslab.geospark.spatialRDD.PointRDD

object TestBasicGeoSpark extends App {

    val conf = new SparkConf().setAppName("GeoSparkRunnableExample").setMaster("local[*]")
    conf.set("spark.serializer", classOf[KryoSerializer].getName)
    conf.set("spark.kryo.registrator", classOf[GeoSparkKryoRegistrator].getName)
    val sc = new SparkContext(conf)
    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    val resourceFolder = System.getProperty("user.dir")+"/src/test/resources/"

    val PointRDDInputLocation = resourceFolder+"arealm-small.csv"
    val PointRDDSplitter = FileDataSplitter.CSV
    val PointRDDIndexType = IndexType.RTREE
    val PointRDDNumPartitions = 5
    val PointRDDOffset = 0

    val PolygonRDDInputLocation = resourceFolder + "county_small.tsv"
    val PolygonRDDSplitter = FileDataSplitter.WKT
    val PolygonRDDNumPartitions = 5
    val PolygonRDDStartOffset = 0
    val PolygonRDDEndOffset = -1

    val geometryFactory=new GeometryFactory()
    val kNNQueryPoint=geometryFactory.createPoint(new Coordinate(-84.01, 34.01))
    val rangeQueryWindow=new Envelope (-90.01,-80.01,30.01,40.01)
    val joinQueryPartitioningType = GridType.QUADTREE
    val eachQueryLoopTimes=5

    var ShapeFileInputLocation = resourceFolder+"shapefiles/polygon"


    testSpatialRangeQuery()

    /**
      * Test spatial range query.
      *
      * @throws Exception the exception
      */
    def testSpatialRangeQuery() {
        val objectRDD = new PointRDD(sc, PointRDDInputLocation, PointRDDOffset, PointRDDSplitter, true, StorageLevel.MEMORY_ONLY)
        objectRDD.rawSpatialRDD.persist(StorageLevel.MEMORY_ONLY)
        for(i <- 1 to eachQueryLoopTimes)
        {
            val resultSize = RangeQuery.SpatialRangeQuery(objectRDD, rangeQueryWindow, false,false).count
        }
    }
}
