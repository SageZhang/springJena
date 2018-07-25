package com.sagezhang.jena.jena;

import org.apache.commons.compress.utils.Lists;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>demo query</p>
 *
 * @author SageZhang
 * @version 2018/7/25
 */
@Component
public class QueryDemo {
    public List<String> query(String param) {
        List<String> list = Lists.newArrayList();
        FileManager.get().addLocatorClassLoader(QueryDemo.class.getClassLoader());
        Model model = FileManager.get().loadModel("data.ttl");
        String queryString =
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
                        "SELECT ?name WHERE { " +
                        "   ?x foaf:name ?" + param +
                        " . " +
                        "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal name = soln.getLiteral("name");
                list.add(name.getString());
            }
        } finally {
            qexec.close();
        }
        return list;
    }
}
