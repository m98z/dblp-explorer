/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import java.util.List;
/**
 *
 * @author melika
 */
public class Paper {
    private String id;
    private String title;
    private int num_of_citations;
    private List<String> references;
    
    public String getId()
    {
        return id;
    }

    public String get_title() 
    {
        return title;
    }
    
    public int get_num_of_citation() 
    {
        return num_of_citations;
    }
    
    public List<String> getReferences()
    {
        return references;
    }

    public boolean containsKeyWord(String keyword)
    {
        return title.toLowerCase().contains(keyword.toLowerCase());
    }
    
    @Override
    public String toString()
    {
        return "Paper{" + "id=" + id + ", title=" + title + ", citation=" + num_of_citations + '}';
    }
}
