/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
/**
 *
 * @author melika
 */
public class Hw1 {
    private final String filePath;
    private final String keyword;

    private final Map<String, Paper> Map_Papers;
    private final List<String> entirePaper;
    private Map<String, String> readingPaper;
    private int tiers = 20;

    public Hw1(String filePath, String keyword) throws IOException 
    {
        this.filePath = filePath;
        this.keyword = keyword;
        Map_Papers = new ConcurrentHashMap<String, Paper>();
        entirePaper = new ArrayList<String>();
        readingPaper = new ConcurrentHashMap<>();
        
        String line;
        List<String> lines;
        List<String> json_items;
        boolean eof = false;

        Scanner scanner = new Scanner(System.in);
        BufferedReader buffer_reader = new BufferedReader( new FileReader(this.filePath));
        scanner.close();
        
        int i = 0;           
        while (!eof) {
            lines = new ArrayList<>();
            while (lines.size() < 5000) {
                if ((line = buffer_reader.readLine()) == null) {
                    eof = true;
                    break;
                } else
                    lines.add(line);
            }
            i += 5000;
            System.out.println("Line number: " + i);
            lines.parallelStream().forEach(l -> createPaper(l));
        }
        buffer_reader.close();
        
        if (readingPaper == null || readingPaper.isEmpty())
            System.out.println("No papers found");

        for( int j = 0; j < tiers; j++) 
        {
            System.out.println("Tier " + (j + 1) + ":\n");
            List<String> buffer = entirePaper
                    .parallelStream()
                    .filter(paper -> paper_is_cited(paper))
                    .sorted()
                    .collect(Collectors.toList());

            if (buffer != null && !buffer.isEmpty()) {
                buffer.forEach( paper_id -> System.out.println(Map_Papers.get(paper_id).toString()));
                readingPaper = buffer.parallelStream()
                        .collect(Collectors.toConcurrentMap(pap -> pap, pap -> pap));
            } else
                System.exit(0);
            
    }
    }
    
    private boolean paper_is_cited( String id ) 
    {
        List<String> list_cite_ref = Map_Papers.get(id).getReferences();
                
        if(list_cite_ref == null) 
        {
            return false;
        }
        else
        {
            return list_cite_ref.parallelStream()
                    .anyMatch(cite -> readingPaper.containsKey(cite.trim()));
        }
    }

    private void createPaper(String line)
    {
        Gson gson = new Gson();
        Paper paper = gson.fromJson(line, Paper.class);
        String id = paper.getId();
        if (paper.containsKeyWord(keyword)){
            System.out.println(paper.toString());
            readingPaper.put(id, id);
        }
        entirePaper.add(id);
        Map_Papers.put(id, paper);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Hw1 explorer_dp = new Hw1("Hw1/dblp_papers_v11.txt", "AI");

    }
    
}
