package src;

import java.util.*;
import java.util.stream.Collectors;

public class Task {
    public record File(
            int id,
            String name,
            List<String> categories,
            int parent,
            int size
    ) {
    }

    /**
     * Task 1
     *
     * leafFiles
     * Given a list of files, it will return the names of the files that have no children
     *
     * @param files The list of files to search
     * @return A list of the names of the files with no children
     */
    public static List<String> leafFiles(List<File> files) {
        // Put files into a hashmap for fast access
        HashMap<Integer, File> map = new HashMap<Integer, File>();
        files.forEach(file -> map.put(file.id, file));
        // Filter off all the parent nodes
        files.stream().filter(file -> file.id != -1).forEach(file -> map.remove(file.parent));
        // Return a all the remaining files' name
        return map.values().stream().map(file -> file.name).collect(Collectors.toList());
    }

    /**
     * Task 2
     * <p>
     * kLargestCategories
     * Given a list of categorised files, it will return the categories that are most prevalent (In descending order)
     * The returned list is limited in length by paramter k
     *
     * @param files The list of files
     * @param k     The number of top categories to return
     * @return A list of top categories in descending order by prevalence
     */
    public static List<String> kLargestCategories(List<File> files, int k) {
        // Tree map as a frequency table
        HashMap<String, Integer> map = new HashMap<>();
        // For each file, for each category in that file, add it to frequency table
        files.forEach(file -> file.categories.forEach(category -> map.merge(category, 1, Integer::sum)));
        // Return top k entries
        return map.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Task 3
     * <p>
     * largestFileSize
     * Takes in a list of files and returns the size of the largest file (Including child files)
     *
     * @param files The list of files
     * @return The largest file size
     */
    public static int largestFileSize(List<File> files) {

        // I tried avoiding tree structures for these problems as a challenge but this one i think its required to not
        // hit n^2 complexity
        class Node {
            int id = -1;
            int size = 0;

            List<Node> children = new ArrayList<>();

            public Node(int id, int size) {
                this.id = id;
                this.size = size;
            }

            public void addChild(Node n) {
                children.add(n);
            }

            public int getSize() {
                int s = size;

                s += children.stream().map(Node::getSize).reduce(0, Integer::sum);

                return s;
            }
        }

        // Put files into a hashmap for fast access
        // ID => Node
        HashMap<Integer, Node> map = new HashMap<Integer, Node>();
        files.forEach(file -> map.put(file.id, new Node(file.id, file.size)));

        // Merge Nodes
        files.stream()
                .filter(file -> file.parent != -1)
                .forEach(file -> map.get(file.parent).addChild(map.get(file.id)));

        // Find root nodes and get size
        return files.stream()
                .filter(file -> file.parent == -1)
                .mapToInt(file -> map.get(file.id).getSize())
                .max()
                .orElse(0);
    }

    public static void main(String[] args) {
        List<File> testFiles = List.of(
                new File(1, "Document.txt", List.of("Documents"), 3, 1024),
                new File(2, "Image.jpg", List.of("Media", "Photos"), 34, 2048),
                new File(3, "Folder", List.of("Folder"), -1, 0),
                new File(5, "Spreadsheet.xlsx", List.of("Documents", "Excel"), 3, 4096),
                new File(8, "Backup.zip", List.of("Backup"), 233, 8192),
                new File(13, "Presentation.pptx", List.of("Documents", "Presentation"), 3, 3072),
                new File(21, "Video.mp4", List.of("Media", "Videos"), 34, 6144),
                new File(34, "Folder2", List.of("Folder"), 3, 0),
                new File(55, "Code.py", List.of("Programming"), -1, 1536),
                new File(89, "Audio.mp3", List.of("Media", "Audio"), 34, 2560),
                new File(144, "Spreadsheet2.xlsx", List.of("Documents", "Excel"), 3, 2048),
                new File(233, "Folder3", List.of("Folder"), -1, 4096)
        );

        List<String> leafFiles = leafFiles(testFiles);
        leafFiles.sort(null);
        assert leafFiles.equals(List.of(
                "Audio.mp3",
                "Backup.zip",
                "Code.py",
                "Document.txt",
                "Image.jpg",
                "Presentation.pptx",
                "Spreadsheet.xlsx",
                "Spreadsheet2.xlsx",
                "Video.mp4"
        ));
        assert kLargestCategories(testFiles, 3).equals(List.of(
                "Documents", "Folder", "Media"
        ));
        assert largestFileSize(testFiles) == 20992;
    }
}
