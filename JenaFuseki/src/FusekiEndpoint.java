import org.apache.jena.query.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class FusekiEndpoint {
    public static void main(String[] args) {
        String sparqlendpoint = "http://localhost:3030/prak/query";
        String perintahSPARQL = "select ?s ?p ?o WHERE {?s ?p ?o}";

        Query query = QueryFactory.create(perintahSPARQL);
        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlendpoint,query);
        ResultSet hasil = qe.execSelect();
//output
        String oo = ResultSetFormatter.asText(hasil);
        System.out.println(oo);

//        String sparqlupdateendpoint = "http://localhost:3030/prak/update";
//        String perintahinsertSPARQL ="prefix f: <http://example.org#> insert data {f:aldo f:hasFriend f:kelvin }";
//
//        UpdateRequest req = UpdateFactory.create(perintahinsertSPARQL);
//        UpdateProcessor reqexnya = UpdateExecutionFactory.createRemote(req,sparqlupdateendpoint);
//        reqexnya.execute();

//        ByteArrayOutputStream b = new ByteArrayOutputStream();
//        ResultSetFormatter.outputAsJSON(b,hasil);
//        String json = b.toString();
//        out.println(json);
    }
}
