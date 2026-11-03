public class Quiz {

    private Question[] questions;

    public Quiz(String language) {

        switch (language) {

            case "C":
                questions = new Question[] {

                    new Question("Who developed C language?",
                        new String[]{"James Gosling", "Dennis Ritchie",
                        "Guido van Rossum", "Bjarne Stroustrup"}, 2),

                    new Question("Which symbol ends a C statement?",
                        new String[]{".", ":", ";", ","}, 3),

                    new Question("Which function is used to print in C?",
                        new String[]{"print()", "cout", "printf()", "display()"}, 3),

                    new Question("Which header file is used for printf()?",
                        new String[]{"stdlib.h", "stdio.h", "string.h", "math.h"}, 2),

                    new Question("Which data type stores an integer?",
                        new String[]{"float", "char", "int", "double"}, 3)
                };
                break;

            case "Python":
                questions = new Question[] {

                    new Question("Who developed Python?",
                        new String[]{"Dennis Ritchie", "James Gosling",
                        "Guido van Rossum", "Bjarne Stroustrup"}, 3),

                    new Question("Which symbol is used for comments in Python?",
                        new String[]{"//", "#", "/*", "--"}, 2),

                    new Question("Which function displays output in Python?",
                        new String[]{"echo()", "printf()", "print()", "display()"}, 3),

                    new Question("Which data type stores True or False?",
                        new String[]{"int", "str", "bool", "float"}, 3),

                    new Question("Which keyword defines a function?",
                        new String[]{"function", "def", "fun", "define"}, 2)
                };
                break;

            case "C++":
                questions = new Question[] {

                    new Question("Who developed C++?",
                        new String[]{"Dennis Ritchie", "James Gosling",
                        "Bjarne Stroustrup", "Guido van Rossum"}, 3),

                    new Question("Which operator is used for output in C++?",
                        new String[]{"cin", "cout", "print", "scanf"}, 2),

                    new Question("Which header is commonly used for cout?",
                        new String[]{"stdio.h", "iostream", "stdlib.h", "string.h"}, 2),

                    new Question("Which keyword creates an object dynamically?",
                        new String[]{"malloc", "new", "create", "object"}, 2),

                    new Question("Which concept allows function overloading?",
                        new String[]{"Polymorphism", "Inheritance",
                        "Encapsulation", "None"}, 1)
                };
                break;

            case "Java":
            default:
                questions = new Question[] {

                    new Question("Which language is platform independent?",
                        new String[]{"C", "Java", "Python", "C++"}, 2),

                    new Question("Which keyword creates an object?",
                        new String[]{"class", "new", "static", "void"}, 2),

                    new Question("What is the entry point of Java?",
                        new String[]{"start()", "run()", "main()", "execute()"}, 3),

                    new Question("Which concept hides data?",
                        new String[]{"Inheritance", "Encapsulation",
                        "Polymorphism", "Abstraction"}, 2),

                    new Question("Which is not a primitive data type?",
                        new String[]{"int", "float", "String", "char"}, 3)
                };
                break;
        }
    }

    public Question[] getQuestions() {
        return questions;
    }

    public String getQuestionsJson() {

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < questions.length; i++) {

            json.append(questions[i].toJson());

            if (i < questions.length - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    public int calculateScore(String[] answers) {

        int score = 0;

        for (int i = 0; i < questions.length; i++) {

            if (i < answers.length) {

                try {
                    int answer = Integer.parseInt(answers[i]);

                    if (questions[i].checkAnswer(answer)) {
                        score++;
                    }

                } catch (NumberFormatException e) {
                    // Invalid answer receives no marks
                }
            }
        }

        return score;
    }
}