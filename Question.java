public class Question {

    private String question;
    private String[] options;
    private int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();

        json.append("{\"question\":\"")
            .append(question)
            .append("\",\"options\":[");

        for (int i = 0; i < options.length; i++) {
            json.append("\"").append(options[i]).append("\"");

            if (i < options.length - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        return json.toString();
    }
}