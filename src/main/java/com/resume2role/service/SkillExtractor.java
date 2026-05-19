package com.resume2role.service;

import com.resume2role.model.SkillSet;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import java.util.*;

@Component
public class SkillExtractor {

    private static final Set<String> LANGUAGES = Set.of(
            "java", "python", "c", "c++", "javascript", "typescript", "go", "kotlin"
    );

    private static final Set<String> FRAMEWORKS = Set.of(
            "spring", "spring boot", "django", "flask", "react", "angular", "node", "express"
    );

    private static final Set<String> DATABASES = Set.of(
            "mongodb", "mysql", "postgresql", "oracle", "redis"
    );

    private static final Set<String> TOOLS = Set.of(
            "git", "docker", "kubernetes", "jenkins", "maven", "gradle", "postman"
    );

    private static final Set<String> CONCEPTS = Set.of(
            "data structures", "algorithms", "oops", "microservices", "rest api",
            "system design", "multithreading"
    );

    public SkillSet extractSkills(String text) {

        text = text.toLowerCase();

        return SkillSet.builder()
                .languages(findMatches(text, LANGUAGES))
                .frameworks(findMatches(text, FRAMEWORKS))
                .databases(findMatches(text, DATABASES))
                .tools(findMatches(text, TOOLS))
                .concepts(findMatches(text, CONCEPTS))
                .build();
    }

    private List<String> findMatches(String text, Set<String> dictionary) {
        List<String> found = new ArrayList<>();

        for (String word : dictionary) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b");
            if (pattern.matcher(text).find()) {
                found.add(word);
            }
        }

        return found;
    }
}