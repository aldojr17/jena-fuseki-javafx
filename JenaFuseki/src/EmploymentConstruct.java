import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

public class EmploymentConstruct {

    public static void main(String[] args){

        String querystring = "PREFIX e:<http://jyu.fi/employment#>" +
                        "PREFIX j:<http://jyu.fi/jobs#>" +
                        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "construct { ?person rdf:type j:Researcher }" +
                        "where {" +
                        "{ ?person e:worksAs j:juniorResearcher }" +
                        "UNION" +
                        "{ ?person e:worksAs j:seniorResearcher }" +
                        "}";

        Model model = ModelFactory.createDefaultModel();
        model.read("employment.rdf","RDF/XML");

        Query query = QueryFactory.create(querystring);
        QueryExecution qe = QueryExecutionFactory.create(query,model);
        model = qe.execConstruct();
//        model.write(System.out,"RDF/XML");

        UpdateRequest req = UpdateFactory.create();
        req.add("prefix f: <http://example.org#> " +
                "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix j: <http://jyu.fi/jobs#> insert data { f:aldo rdf:type j:Researcher } ");
        req.add("prefix f: <http://example.org#> " +
                "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "prefix j: <http://jyu.fi/jobs#> delete data { f:mary rdf:type j:Researcher }");
        UpdateAction.execute(req,model);
        model.write(System.out, "RDF/XML");
    }
}