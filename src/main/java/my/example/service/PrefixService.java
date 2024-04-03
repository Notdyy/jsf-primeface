package my.example.service;

import java.util.ArrayList;
import java.util.List;

import my.example.model.Prefix;

public class PrefixService {
    public List<Prefix> getAllPrefixes() {
        List<Prefix> prefixes = new ArrayList<>();
        prefixes.add(new Prefix("Select Prefix", ""));
        prefixes.add(new Prefix("Mrs.", "1"));
        prefixes.add(new Prefix("Mr.", "2"));
        prefixes.add(new Prefix("Miss", "3"));
        prefixes.add(new Prefix("Ms.", "4"));
        return prefixes;
    }

}
