import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

public class JobsConstruct {

    public static void main(String[] args){

        String querystring = "PREFIX j:<http://jyu.fi/jobs#>" +
                        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "construct { ?job rdf:type j:MultiJob }" +
                        "where {" +
                        "{ ?job rdf:type j:ResearchJob }" +
                        "{ ?job rdf:type j:EducationJob }" +
                        "}";

        Model model = ModelFactory.createDefaultModel();
        model.read("jobs.rdf","RDF/XML");

        Query query = QueryFactory.create(querystring);
        QueryExecution qe = QueryExecutionFactory.create(query,model);
        model = qe.execConstruct();
//        model.write(System.out,"RDF/XML");

        UpdateRequest req = UpdateFactory.create();
        req.add("prefix f: <http://example.org#> " +
                "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix j: <http://jyu.fi/jobs#> insert data { f:kelvin f:worksAs j:professor } ");
        req.add("prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix j: <http://jyu.fi/jobs#> delete data { j:professor rdf:type j:MultiJob }");
        UpdateAction.execute(req,model);
        model.write(System.out, "RDF/XML");
    }
}