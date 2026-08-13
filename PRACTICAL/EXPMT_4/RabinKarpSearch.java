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

public class RabinKarpSearch {

    // Rabin-Karp Pattern Matching
    public static void rabinKarpSearch(String text, String pattern, Article article) {

        int n = text.length();
        int m = pattern.length();

        int prime = 101;
        int base = 256;

        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        int count = 0;

        // Calculate base^(m-1)
        for (int i = 0; i < m - 1; i++) {
            h = (h * base) % prime;
        }

        // Calculate initial hash values
        for (int i = 0; i < m; i++) {
            patternHash =
                    (base * patternHash + pattern.charAt(i)) % prime;

            textHash =
                    (base * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over the text
        for (int i = 0; i <= n - m; i++) {

            // If hash values match, compare characters
            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {

                    System.out.println(
                            "Pattern found at position : " + i
                    );

                    count++;
                }
            }

            // Calculate hash for next window
            if (i < n - m) {

                textHash =
                        (base * (textHash - text.charAt(i) * h)
                        + text.charAt(i + m)) % prime;

                if (textHash < 0) {
                    textHash += prime;
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

                br.readLine(); // Skip blank line

                StringBuilder content = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {

                    content.append(line).append(" ");
                }

                Article article =
                        new Article(
                                id,
                                title,
                                content.toString().trim()
                        );

                repository.add(article);

                id++;

                br.close();

            } catch (IOException e) {

                System.out.println(
                        "Cannot read file : " + fileName
                );
            }
        }


        // User input
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("       TEXTHACK RABIN-KARP SEARCH");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String pattern = sc.nextLine().toLowerCase();


        System.out.println();
        System.out.println("=====================================");
        System.out.println("       RABIN-KARP PATTERN SEARCH");
        System.out.println("=====================================");


        // Search every article
        for (Article article : repository) {

            String text =
                    (article.title + " " + article.content).toLowerCase();

            if (text.contains(pattern)) {

                System.out.println(
                        "Article ID : " + article.id
                );

                System.out.println(
                        "Title : " + article.title
                );

                rabinKarpSearch(text, pattern, article);

                System.out.println("----------------------------------------");
            }
        }

        sc.close();
    }
}