let questions = [];
let currentQuestion = 0;
let answers = [];
let selectedLanguage = "Java";

async function startQuiz() {

    selectedLanguage = document.getElementById("language").value;

    const response = await fetch(
        "/api/questions?language=" +
        encodeURIComponent(selectedLanguage)
    );

    questions = await response.json();

    currentQuestion = 0;
    answers = [];

    document.getElementById("languageSection").style.display = "none";
    document.getElementById("quiz").style.display = "block";
    document.getElementById("result").style.display = "none";

    showQuestion();
}

function showQuestion() {

    let q = questions[currentQuestion];

    document.getElementById("question").innerText =
        "Question " + (currentQuestion + 1) + ": " + q.question;

    let optionsDiv = document.getElementById("options");

    optionsDiv.innerHTML = "";

    q.options.forEach((option, index) => {

        optionsDiv.innerHTML += `
            <label class="option">
                <input type="radio" name="answer"
                value="${index + 1}">
                ${option}
            </label>
        `;
    });

    document.getElementById("nextBtn").innerText =
        currentQuestion === questions.length - 1
        ? "Submit Quiz" : "Next Question";
}

async function nextQuestion() {

    let selected = document.querySelector(
        'input[name="answer"]:checked'
    );

    if (!selected) {
        alert("Please select an answer!");
        return;
    }

    answers[currentQuestion] = selected.value;

    if (currentQuestion < questions.length - 1) {

        currentQuestion++;
        showQuestion();

    } else {

        await submitQuiz();
    }
}

async function submitQuiz() {

    const params = new URLSearchParams({
        language: selectedLanguage,
        answers: answers.join(",")
    });

    const response = await fetch("/api/submit?" + params);

    const result = await response.json();

    document.getElementById("quiz").style.display = "none";
    document.getElementById("result").style.display = "block";

    document.getElementById("score").innerText =
        selectedLanguage + " Quiz Score: " +
        result.score + "/" + result.total;

    document.getElementById("percentage").innerText =
        "Percentage: " + result.percentage + "% - " +
        (result.passed ? "PASS" : "FAIL");
}

function restartQuiz() {

    document.getElementById("result").style.display = "none";

    document.getElementById("languageSection").style.display = "block";
}