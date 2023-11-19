import java.util.*;
 
public class PageReplacement {

    public static void FIFO(int numFrames, List<Integer> pages) {
        Queue<Integer> frameQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();

        int pageFaults = 0;

        for (int page : pages) {
            if (!frameSet.contains(page)) {
                if (frameQueue.size() == numFrames) {
                    int removedPage = frameQueue.poll();
                    frameSet.remove(removedPage);
                }
                frameQueue.offer(page);
                frameSet.add(page);

                pageFaults++;

                System.out.println("Page Reference: " + page + " | Status: Miss");
            } else {
                System.out.println("Page Reference: " + page + " | Status: Hit");
            }
        }
        System.out.println("Total Page Faults: " + pageFaults);
    }

    public static int LRU(int capacity, List<Integer> pages) {
        Set<Integer> s = new HashSet<>();
        int n = pages.size();
        Map<Integer, Integer> indexes = new HashMap<>();

        int pageFaults = 0;
        for (int i = 0; i < n; i++) {
            if (s.size() < capacity) {
                if (!s.contains(pages.get(i))) {
                    s.add(pages.get(i));
                    pageFaults++;
                }
                indexes.put(pages.get(i), i);
            } else {
                if (!s.contains(pages.get(i))) {
                    int lru = Integer.MAX_VALUE;
                    int val = -1;

                    for (int page : s) {
                        if (indexes.get(page) < lru) {
                            lru = indexes.get(page);
                            val = page;
                        }
                    }

                    s.remove(val);
                    s.add(pages.get(i));
                    pageFaults++;
                }
                indexes.put(pages.get(i), i);
            }
        }

        return pageFaults;
    }

    public static int optimalPageReplacement(int capacity, List<Integer> pages) {
        int pageFaults = 0;
        Map<Integer, Integer> pageIndices = new HashMap<>();
        List<Integer> frames = new ArrayList<>(Collections.nCopies(capacity, Integer.MAX_VALUE));

        for (int i = 0; i < pages.size(); ++i) {
            int page = pages.get(i);

            if (pageIndices.containsKey(page)) {
                frames.set(pageIndices.get(page), i);
            } else {
                int maxIndex = 0;
                int nextPage = frames.get(0);

                for (int j = 0; j < capacity; ++j) {
                    if (frames.get(j) == Integer.MAX_VALUE) {
                        maxIndex = j;
                        nextPage = Integer.MAX_VALUE;
                        break;
                    }

                    if (frames.get(j) > frames.get(maxIndex)) {
                        maxIndex = j;
                        nextPage = frames.get(j);
                    }
                }

                frames.set(maxIndex, page);
                pageIndices.put(page, i);
                pageIndices.put(nextPage, Integer.MAX_VALUE);
                pageFaults++;
            }
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

        System.out.println("\nFIFO Algorithm:");
        FIFO(numFrames, new ArrayList<>(pages));

        System.out.println("\nLRU Algorithm:");
        int lruPageFaults = LRU(numFrames, new ArrayList<>(pages));
        System.out.println("Total Page Faults (LRU): " + lruPageFaults);

        System.out.println("\nOptimal Page Replacement Algorithm:");
        int optimalPageFaults = optimalPageReplacement(numFrames, new ArrayList<>(pages));
        System.out.println("Total Page Faults (Optimal): " + optimalPageFaults);
    }
}
