import java.io.*;
import java.util.*;

public class UI {
    private ArrayList<String> questions;
    private int[] answers;
    private Map<String, Set<String>> knowledgeBase = new HashMap<>();

    public UI() {
        this.answers = new int[8];  // Array untuk menyimpan jawaban (1: Ya, 0: Tidak)
        this.questions = new ArrayList<>();
        this.setQuestions();
       
    }           
                                                                                    
    // Mengisi daftar pertanyaan
    private void setQuestions() {
        questions.add("Apakah daun jagung mengalami warna kuning (chlorotic)?");
        questions.add("Apakah tanaman jagung mengalami pertumbuhan yang terhambat?");
        questions.add("Apakah daun jagung memiliki warna putih seperti tepung?");
        questions.add("Apakah daun jagung menggulung dan melintir?");
        questions.add("Apakah pembentukan tongkol terganggu?");
        questions.add("Apakah terdapat bercak-bercak cokelat pada daun?");
        questions.add("Apakah terdapat karat pada daun?");
        questions.add("Apakah daun terlihat terbakar?");
    }

    // Membaca knowledge_base.txt dan mengisi peta penyakit ke dalam HashMap
   

    // Menampilkan pertanyaan kepada pengguna
    public void showQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jawab dengan '1' untuk ya dan '0' untuk tidak.");
        
        // Loop untuk menampilkan setiap pertanyaan dan mengambil jawaban
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i));
            int answer = scanner.nextInt();
            // Validasi jawaban
            while (answer != 0 && answer != 1) {
                System.out.println("Jawaban tidak valid. Masukkan '1' untuk ya dan '0' untuk tidak.");
                answer = scanner.nextInt();
            }
            answers[i] = answer;  // Simpan jawaban dalam array
        }
    }

    // Mengembalikan fakta-fakta yang sesuai dengan jawaban pengguna
    public Set<String> getFacts() {
        Set<String> facts = new HashSet<>();
        if (answers[0] == 1) facts.add("chlorotic_colored_leaves");
        if (answers[1] == 1) facts.add("stunted_growth");
        if (answers[2] == 1) facts.add("white_like_flour");
        if (answers[3] == 1) facts.add("leaves_curl_twist");
        if (answers[4] == 1) facts.add("disturbed_cob");
        if (answers[5] == 1) facts.add("brown_spots_on_leaves");
        if (answers[6] == 1) facts.add("rust_on_leaves");
        if (answers[7] == 1) facts.add("burned_leaves");
        return facts;
    }

    // Menginferensikan penyakit berdasarkan gejala yang diperoleh
    public void inferDisease(Set<String> facts) {
        String penyakitDitemukan = "";
        int maxMatchedSymptoms = 0;
        Set<String> inferedFacts = new HashSet<>();

        // Cek kecocokan untuk setiap penyakit dari knowledge base
        for (Map.Entry<String, Set<String>> entry : knowledgeBase.entrySet()) {
            String disease = entry.getKey();
            Set<String> diseaseFacts = entry.getValue();

            int matches = matchCount(diseaseFacts, facts);
            if (matches > maxMatchedSymptoms) {
                maxMatchedSymptoms = matches;
                penyakitDitemukan = disease;
                inferedFacts = diseaseFacts;
            }
        }

        if (maxMatchedSymptoms > 0) {
            showConclusion(true, facts, inferedFacts, penyakitDitemukan);
        } else {
            showConclusion(false, facts, inferedFacts, penyakitDitemukan);
        }
    }

    // Menghitung jumlah gejala yang cocok antara penyakit dan fakta pengguna
    public int matchCount(Set<String> diseaseFacts, Set<String> userFacts) {
        int count = 0;
        for (String fact : diseaseFacts) {
            if (userFacts.contains(fact)) {
                count++;
            }
        }
        return count;
    }

    // Menampilkan kesimpulan hasil inferensi
    void showConclusion(boolean penyakitTeridentifikasi, Set<String> facts, Set<String> inferedFacts, String penyakitDitemukan) {
        if (penyakitTeridentifikasi) {
            System.out.println("Penyakit yang teridentifikasi: " + penyakitDitemukan);
            System.out.println("Gejala yang mendukung: " + inferedFacts);
        } else {
            System.out.println("Tidak cukup fakta untuk mengidentifikasi penyakit.");
        }
        System.out.println("Fakta yang diperoleh: " + facts);
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.showQuestion();  // Tampilkan pertanyaan ke pengguna
        Set<String> userFacts = ui.getFacts();  // Ambil fakta-fakta dari jawaban pengguna
        ui.inferDisease(userFacts);  // Inferensikan penyakit berdasarkan fakta
    }
}
