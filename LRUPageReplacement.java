import java.util.*;

public class LRUPageReplacement {

    public static int LRU(int capacity, List<Integer> pages) {
        Set<Integer> s = new LinkedHashSet<>();
        Map<Integer, Integer> indexes = new HashMap<>();

        int pageFaults = 0;
        for (int i = 0; i < pages.size(); i++) {
            int page = pages.get(i);

            if (s.size() < capacity) {
                if (!s.contains(page)) {
                    s.add(page);
                    pageFaults++;
                }
            } else {
                if (!s.contains(page)) {
                    int lru = Integer.MAX_VALUE;
                    int val = -1;

                    for (int p : s) {
                        if (indexes.get(p) < lru) {
                            lru = indexes.get(p);
                            val = p;
                        }
                    }

                    s.remove(val);
                    pageFaults++;
                }
            }

            s.add(page);
            indexes.put(page, i);

            System.out.println("Page Reference: " + page + " | Status: " + (s.contains(page) ? "Hit" : "Miss"));
        }

        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        System.out.print("Enter the number of pages: ");
        int numPages = scanner.nextInt();

        System.out.print("Enter the page reference string: ");
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < numPages; ++i) {
            pages.add(scanner.nextInt());
        }

        int lruPageFaults = LRU(numFrames, new ArrayList<>(pages));
        System.out.println("Total Page Faults (LRU): " + lruPageFaults);
    }
}
