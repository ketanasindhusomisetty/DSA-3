import java.io.*;
import java.util.*;

class Article {

    int id;
    String title;
    String content;
    int wordCount;

    public Article(int id, String title, String content) {

        this.id = id;
        this.title = title;
        this.content = content;

        wordCount = content.trim().split("\\s+").length;
    }

    public void display() {

        System.out.println("----------------------------------------");
        System.out.println("Article ID : " + id);
        System.out.println("Title : " + title);
        System.out.println("Word Count : " + wordCount);
        System.out.println("Content :");
        System.out.println(content);
        System.out.println("----------------------------------------");
    }
}

public class QueryRetrieval {

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
                        new Article(id, title, content.toString().trim());

                repository.add(article);

                id++;

                br.close();

            } catch (IOException e) {

                System.out.println("Cannot read file : " + fileName);
            }
        }

        // Query Processor
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("       TEXTHACK QUERY PROCESSOR");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String keyword = sc.nextLine().toLowerCase();

        System.out.println();
        System.out.println("Matching Articles");

        boolean found = false;

        // Search keyword in title and content
        for (Article article : repository) {

            if (article.title.toLowerCase().contains(keyword)
                    || article.content.toLowerCase().contains(keyword)) {

                article.display();

                found = true;
            }
        }

        if (!found) {

            System.out.println("No matching articles found.");
        }

        sc.close();
    }
}