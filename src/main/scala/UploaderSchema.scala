import org.apache.avro.Schema
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient

import scala.io.Source


class UploaderSchema {

}

object UploaderSchema {
  def main(args: Array[String]): Unit = {
    val avroSchema = Source.fromInputStream(getClass.getResourceAsStream("/schema.json")).mkString

    println(avroSchema)
    val schema_registry_url = "http://localhost:8081"
    val schema = new Schema.Parser().parse(avroSchema)
    val topic_schema = "default"
    println(schema)
    val payload = "{ \"schema\": \"" + schema + "\" }"
    val url = schema_registry_url + "/subjects/" + topic_schema + "-value/versions"
    val post = new HttpPost(url)
    post.addHeader("Content-Type", "application/vnd.schemaregistry.v1+json")
    post.setEntity(new StringEntity(schema.toString()))
    val response = (new DefaultHttpClient).execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))

  }



}