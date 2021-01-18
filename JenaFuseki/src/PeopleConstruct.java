import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

public class PeopleConstruct {

    public static void main(String[] args){

        String querystring = "PREFIX f:<http://example.org#>" +
                        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "construct { ?person rdf:type f:LovesYoungGirl }" +
                        "where {" +
                        "{ ?person f:loves ?girl }" +
                        "{ ?girl f:age ?age FILTER(?age < 25) }" +
                        "}";

        Model model = ModelFactory.createDefaultModel();
        model.read("people.rdf","RDF/XML");

        Query query = QueryFactory.create(querystring);
        QueryExecution qe = QueryExecutionFactory.create(query,model);
        model = qe.execConstruct();
//        model.write(System.out,"RDF/XML");

        UpdateRequest req = UpdateFactory.create();
        req.add("prefix f: <http://example.org#> " +
                "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> insert data { f:john f:loves " +
                "f:mary } ");
        req.add("prefix f: <http://example.org#> " +
                "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> delete data { f:john rdf:type " +
                "f:LovesYoungGirl }");
        UpdateAction.execute(req,model);
        model.write(System.out, "RDF/XML");
    }
}