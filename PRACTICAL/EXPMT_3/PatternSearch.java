import java.io.*;
import java.util.*;

class Article {

    int id;
    String title;
    String content;

    public Article(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

public class PatternSearch {

    // Naive Pattern Matching
    public static void naiveSearch(String text, String pattern, Article article) {

        int n = text.length();
        int m = pattern.length();

        int count = 0;

        System.out.println("=====================================");
        System.out.println("      NAIVE PATTERN MATCHING");
        System.out.println("=====================================");
        System.out.println("Article ID : " + article.id);
        System.out.println("Title : " + article.title);

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {

                System.out.println("Pattern found at position : " + i);

                count++;
            }
        }

        System.out.println("Total occurrences : " + count);
    }


    // Create LPS array for KMP
    public static int[] createLPS(String pattern) {

        int m = pattern.length();

        int[] lps = new int[m];

        int len = 0;

        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(len)) {

                len++;

                lps[i] = len;

                i++;

            } else {

                if (len != 0) {

                    len = lps[len - 1];

                } else {

                    lps[i] = 0;

                    i++;
                }
            }
        }

        return lps;
    }


    // KMP Pattern Matching
    public static void kmpSearch(String text, String pattern, Article article) {

        int n = text.length();
        int m = pattern.length();

        int[] lps = createLPS(pattern);

        int i = 0;
        int j = 0;

        int count = 0;

        System.out.println("=====================================");
        System.out.println("          KMP PATTERN MATCHING");
        System.out.println("=====================================");
        System.out.println("Article ID : " + article.id);
        System.out.println("Title : " + article.title);

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

            }

            if (j == m) {

                System.out.println("Pattern found at position : " + (i - j));

                count++;

                j = lps[j - 1];

            } else if (i < n &&
                       text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {

                    j = lps[j - 1];

                } else {

                    i++;
                }
            }
        }

        System.out.println("Total occurrences : " + count);
    }


    public static void main(String[] args) {

        ArrayList<Article> repository = new ArrayList<>();

        String[] files = {"a1.txt", "a2.txt", "a3.txt"};

        int id = 101;


        // Load articles
        for (String fileName : files) {

            try {

                File file = new File("corpus/" + fileName);

                BufferedReader br =
                        new BufferedReader(new FileReader(file));

                String title = br.readLine();

                br.readLine();

                StringBuilder content = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {

                    content.append(line).append(" ");
                }

                Article article =
                        new Article(id, title, content.toString().trim());

                repository.add(article);

                id++;

                br.close();

            } catch (IOException e) {

                System.out.println("Cannot read file : " + fileName);
            }
        }


        // User input
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("       TEXTHACK PATTERN SEARCH");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String pattern = sc.nextLine();


        // Search in every article
        for (Article article : repository) {

            String text = article.title + " " + article.content;

            if (text.toLowerCase().contains(pattern.toLowerCase())) {

                naiveSearch(text.toLowerCase(),
                            pattern.toLowerCase(),
                            article);

                System.out.println();

                kmpSearch(text.toLowerCase(),
                          pattern.toLowerCase(),
                          article);

                System.out.println();
            }
        }

        sc.close();
    }
}