var editor;

/* ===========================
   Mobile Sidebar Toggle
   =========================== */

(function() {
    document.addEventListener('DOMContentLoaded', function() {
        var toggle = document.getElementById('sidebarToggle');
        var sidebar = document.getElementById('sidebar');
        var overlay = document.getElementById('sidebarOverlay');
        if (!toggle || !sidebar) return;

        function openSidebar() {
            sidebar.classList.add('open');
            if (overlay) {
                overlay.style.display = 'block';
                requestAnimationFrame(function() {
                    overlay.classList.add('visible');
                });
            }
            toggle.innerHTML = '✕';
            document.body.style.overflow = 'hidden';
        }

        function closeSidebar() {
            sidebar.classList.remove('open');
            if (overlay) {
                overlay.classList.remove('visible');
                setTimeout(function() { overlay.style.display = 'none'; }, 300);
            }
            toggle.innerHTML = '☰';
            document.body.style.overflow = '';
        }

        toggle.addEventListener('click', function() {
            if (sidebar.classList.contains('open')) {
                closeSidebar();
            } else {
                openSidebar();
            }
        });

        if (overlay) {
            overlay.addEventListener('click', closeSidebar);
        }

        sidebar.querySelectorAll('.nav-link').forEach(function(link) {
            link.addEventListener('click', function() {
                if (window.innerWidth <= 768) {
                    closeSidebar();
                }
            });
        });

        window.addEventListener('resize', function() {
            if (window.innerWidth > 768 && sidebar.classList.contains('open')) {
                closeSidebar();
            }
        });
    });
})();

/* ===========================
   Code Editor & Exercise Page
   =========================== */

function initEditor(lang) {
    var textarea = document.getElementById('codeEditor');
    if (!textarea) return;

    var editorMode = (lang === 'sql') ? 'text/x-sql' : 'text/x-java';

    editor = CodeMirror.fromTextArea(textarea, {
        mode: editorMode,
        theme: 'dracula',
        lineNumbers: true,
        matchBrackets: true,
        autoCloseBrackets: true,
        indentUnit: 4,
        tabSize: 4,
        indentWithTabs: false,
        lineWrapping: true,
        extraKeys: {
            'Ctrl-Enter': function() { checkCode(); },
            'Shift-Enter': function() { runCode(); },
            'Tab': function(cm) {
                cm.replaceSelection('    ', 'end');
            }
        }
    });

    var saved = localStorage.getItem('code_' + topicId + '_' + exerciseId);
    if (saved) {
        editor.setValue(saved);
    }

    editor.on('change', function() {
        localStorage.setItem('code_' + topicId + '_' + exerciseId, editor.getValue());
    });

    hljs.highlightAll();
    addCopyButtons();
    updateExerciseNavStatus();
}

function initExerciseNav() {
    var panelTabs = document.querySelectorAll('.panel-tab');
    panelTabs.forEach(function(tab) {
        tab.addEventListener('click', function() {
            var panelId = tab.getAttribute('data-panel');
            panelTabs.forEach(function(t) { t.classList.remove('active'); });
            tab.classList.add('active');
            document.querySelectorAll('.panel-content').forEach(function(p) {
                p.style.display = 'none';
            });
            var target = document.getElementById(panelId);
            if (target) target.style.display = 'block';
        });
    });
}

function runCode() {
    var code = editor.getValue().trim();
    if (!code) {
        showResult({
            success: false,
            error: 'Vui long nhap code truoc khi chay.',
            score: 0,
            feedback: ''
        });
        return;
    }

    var btn = document.getElementById('runBtn');
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner"></span> Dang chay...';

    fetch('/api/check', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            code: code,
            topicId: topicId,
            exerciseId: exerciseId
        })
    })
    .then(function(response) { return response.json(); })
    .then(function(result) {
        result._runOnly = true;
        showResult(result);
    })
    .catch(function(err) {
        showResult({
            success: false,
            error: 'Loi ket noi: ' + err.message,
            score: 0,
            feedback: 'Khong the ket noi den may chu.'
        });
    })
    .finally(function() {
        btn.disabled = false;
        btn.innerHTML = '▶ Chạy (Run)';
    });
}

function checkCode() {
    var code = editor.getValue().trim();
    if (!code) {
        showResult({
            success: false,
            error: 'Vui lòng nhập code trước khi kiểm tra.',
            score: 0,
            feedback: ''
        });
        return;
    }

    var btn = document.getElementById('checkBtn');
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner"></span> Đang kiểm tra...';

    fetch('/api/check', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            code: code,
            topicId: topicId,
            exerciseId: exerciseId
        })
    })
    .then(function(response) { return response.json(); })
    .then(function(result) {
        showResult(result);
        if (result.success && result.score >= 40) {
            saveExerciseResult(topicId, exerciseId, result.score);
        }
    })
    .catch(function(err) {
        showResult({
            success: false,
            error: 'Lỗi kết nối: ' + err.message,
            score: 0,
            feedback: 'Không thể kết nối đến máy chủ.'
        });
    })
    .finally(function() {
        btn.disabled = false;
        btn.innerHTML = '✅ Nộp Bài (Submit)';
    });
}

function showResult(result) {
    var panel = document.getElementById('resultPanel');
    var header = document.getElementById('resultHeader');
    var scoreDiv = document.getElementById('resultScore');
    var feedback = document.getElementById('resultFeedback');
    var output = document.getElementById('resultOutput');
    var errorDiv = document.getElementById('resultError');
    var outputWrapper = document.querySelector('.result-output-wrapper');

    panel.style.display = 'block';

    if (result._runOnly) {
        if (result.success) {
            header.className = 'result-header success';
            header.textContent = '▶ Chạy thành công!';
        } else {
            header.className = 'result-header error';
            header.textContent = '▶ Có lỗi khi chạy';
        }
        scoreDiv.innerHTML = '';
        feedback.textContent = '';
    } else {
        if (result.success) {
            header.className = 'result-header success';
            header.textContent = '✅ Biên dịch và chạy thành công!';
        } else {
            header.className = 'result-header error';
            header.textContent = '❌ Có lỗi xảy ra';
        }

        var score = result.score || 0;
        var color = score >= 80 ? '#22c55e' : (score >= 60 ? '#f59e0b' : (score >= 40 ? '#f97316' : '#ef4444'));
        scoreDiv.innerHTML = '<strong>Điểm: ' + score + '/100</strong>' +
            '<div class="score-bar"><div class="score-fill" style="width:' + score + '%;background:' + color + '"></div></div>';

        feedback.textContent = result.feedback || '';
    }

    if (result.output) {
        output.textContent = result.output;
        output.style.display = 'block';
        if (outputWrapper) outputWrapper.style.display = 'block';
    } else {
        output.style.display = 'none';
        if (outputWrapper) outputWrapper.style.display = 'none';
    }

    if (result.error) {
        errorDiv.textContent = result.error;
        errorDiv.style.display = 'block';
    } else {
        errorDiv.style.display = 'none';
    }

    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function closeResult() {
    document.getElementById('resultPanel').style.display = 'none';
}

function showSolutionTab() {
    var tabs = document.querySelectorAll('.panel-tab');
    tabs.forEach(function(t) { t.classList.remove('active'); });
    document.getElementById('solutionTabBtn').classList.add('active');
    document.querySelectorAll('.panel-content').forEach(function(p) {
        p.style.display = 'none';
    });
    document.getElementById('solution-tab').style.display = 'block';
    hljs.highlightAll();
    addCopyButtons();
}

function switchSolution(btn) {
    var idx = btn.getAttribute('data-sol-idx');
    document.querySelectorAll('.solution-tab-btn').forEach(function(b) {
        b.classList.remove('active');
    });
    btn.classList.add('active');
    document.querySelectorAll('.solution-content').forEach(function(c) {
        c.style.display = 'none';
    });
    var target = document.getElementById('sol-' + idx);
    if (target) {
        target.style.display = 'block';
        hljs.highlightAll();
        addCopyButtons();
    }
}

function toggleSolution() {
    var panel = document.getElementById('solutionPanel');
    var btn = document.getElementById('showSolutionBtn');

    if (panel.style.display === 'none') {
        panel.style.display = 'block';
        btn.innerHTML = '🙈 Ẩn Đáp Án';
        hljs.highlightAll();
        addCopyButtons();
        panel.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } else {
        panel.style.display = 'none';
        btn.innerHTML = '👁️ Xem Đáp Án';
    }
}

function resetCode() {
    if (confirm('Bạn có chắc muốn xóa code hiện tại và bắt đầu lại?')) {
        var defaultCode;
        if (typeof editorLang !== 'undefined' && editorLang === 'sql') {
            defaultCode = '-- Viết câu lệnh SQL của bạn ở đây\n-- Dữ liệu mẫu: phong_ban, nhan_vien, du_an, phan_cong\n\nSELECT * FROM nhan_vien LIMIT 5;\n';
        } else {
            defaultCode = 'public class Solution {\n    public static void main(String[] args) {\n        // Viết code của bạn ở đây\n\n    }\n}';
        }
        editor.setValue(defaultCode);
        localStorage.removeItem('code_' + topicId + '_' + exerciseId);
        document.getElementById('resultPanel').style.display = 'none';
    }
}

/* ===========================
   Progress Tracking (localStorage)
   =========================== */

function saveExerciseResult(topicId, exerciseId, score) {
    var key = 'progress_' + topicId;
    var progress = JSON.parse(localStorage.getItem(key) || '{}');
    var prevScore = progress[exerciseId] || 0;
    if (score > prevScore) {
        progress[exerciseId] = score;
        localStorage.setItem(key, JSON.stringify(progress));
    }
    updateExerciseNavStatus();
}

function getTopicProgress(topicId) {
    var key = 'progress_' + topicId;
    return JSON.parse(localStorage.getItem(key) || '{}');
}

function updateExerciseNavStatus() {
    if (typeof topicId === 'undefined') return;
    var progress = getTopicProgress(topicId);
    var icons = document.querySelectorAll('.exercise-status-icon');
    icons.forEach(function(icon) {
        var id = icon.id.replace('nav-status-', '');
        if (progress[id] && progress[id] >= 40) {
            icon.textContent = '✅';
        }
    });
}

/* ===========================
   Topic Page
   =========================== */

function initTopicPage(topicId, totalExercises) {
    var progress = getTopicProgress(topicId);
    var completedCount = 0;

    for (var i = 1; i <= totalExercises; i++) {
        var score = progress[i] || 0;
        var dot = document.getElementById('status-dot-' + i);
        var scoreBadge = document.getElementById('score-' + i);
        var card = document.getElementById('exercise-card-' + i);

        if (score >= 40) {
            completedCount++;
            if (dot) dot.classList.add('done');
            if (card) card.classList.add('completed');
            if (scoreBadge) {
                scoreBadge.textContent = score + ' điểm';
                scoreBadge.classList.add('visible');
                if (score >= 80) scoreBadge.classList.add('high');
                else if (score >= 60) scoreBadge.classList.add('medium');
                else scoreBadge.classList.add('low');
            }
        }
    }

    var countEl = document.getElementById('progressCount');
    if (countEl) countEl.textContent = completedCount + ' / ' + totalExercises + ' bài';

    var fillEl = document.getElementById('progressFill');
    if (fillEl) {
        var pct = totalExercises > 0 ? (completedCount / totalExercises * 100) : 0;
        fillEl.style.width = pct + '%';
    }
}

function filterExercises(filter, btn) {
    document.querySelectorAll('.filter-btn').forEach(function(b) {
        b.classList.remove('active');
    });
    btn.classList.add('active');

    var cards = document.querySelectorAll('.exercise-card');
    cards.forEach(function(card) {
        var isCompleted = card.classList.contains('completed');
        if (filter === 'all') {
            card.classList.remove('hidden');
        } else if (filter === 'completed') {
            card.classList.toggle('hidden', !isCompleted);
        } else if (filter === 'pending') {
            card.classList.toggle('hidden', isCompleted);
        }
    });
}

/* ===========================
   Home Page
   =========================== */

function initHomePage(topicsData) {
    var totalExercises = 0;
    var totalCompleted = 0;

    topicsData.forEach(function(topic) {
        var progress = getTopicProgress(topic.id);
        var completed = 0;
        for (var key in progress) {
            if (progress[key] >= 40) completed++;
        }
        totalExercises += topic.exerciseCount;
        totalCompleted += completed;

        var progressBadge = document.getElementById('home-progress-' + topic.id);
        if (progressBadge && completed > 0) {
            progressBadge.textContent = completed + '/' + topic.exerciseCount + ' hoàn thành';
            progressBadge.style.display = 'inline-block';
        }

        var barEl = document.getElementById('home-bar-' + topic.id);
        if (barEl) {
            var fill = barEl.querySelector('.topic-mini-progress-fill');
            if (fill && topic.exerciseCount > 0) {
                fill.style.width = (completed / topic.exerciseCount * 100) + '%';
            }
        }
    });

    var heroStats = document.getElementById('heroStats');
    if (heroStats) {
        heroStats.innerHTML =
            '<div class="hero-stat"><div class="hero-stat-value">' + topicsData.length + '</div><div class="hero-stat-label">Chủ đề</div></div>' +
            '<div class="hero-stat"><div class="hero-stat-value">' + totalExercises + '</div><div class="hero-stat-label">Bài tập</div></div>' +
            '<div class="hero-stat"><div class="hero-stat-value">' + totalCompleted + '</div><div class="hero-stat-label">Đã hoàn thành</div></div>';
    }
}

/* ===========================
   Theory Page
   =========================== */

function initTheoryPage() {
    hljs.highlightAll();
    addCopyButtons();
    buildTableOfContents();
    initReadingProgress();
    initScrollToTop();
    initTocToggle();
}

function buildTableOfContents() {
    var content = document.getElementById('theoryContent');
    var tocNav = document.getElementById('tocNav');
    if (!content || !tocNav) return;

    var headings = content.querySelectorAll('h1, h2, h3');
    if (headings.length === 0) return;

    var html = '';
    headings.forEach(function(heading, idx) {
        var id = 'heading-' + idx;
        heading.id = id;
        var tag = heading.tagName.toLowerCase();
        var cls = 'toc-link';
        if (tag === 'h3') cls += ' toc-h3';
        else if (tag === 'h4') cls += ' toc-h4';
        html += '<a href="#' + id + '" class="' + cls + '" data-target="' + id + '">' + heading.textContent + '</a>';
    });
    tocNav.innerHTML = html;

    var tocLinks = tocNav.querySelectorAll('.toc-link');
    var observer = new IntersectionObserver(function(entries) {
        entries.forEach(function(entry) {
            if (entry.isIntersecting) {
                tocLinks.forEach(function(link) { link.classList.remove('active'); });
                var active = tocNav.querySelector('[data-target="' + entry.target.id + '"]');
                if (active) active.classList.add('active');
            }
        });
    }, { rootMargin: '-20% 0px -70% 0px' });

    headings.forEach(function(h) { observer.observe(h); });

    tocLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            var targetId = link.getAttribute('data-target');
            var target = document.getElementById(targetId);
            if (target) {
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
}

function initReadingProgress() {
    var progressBar = document.getElementById('readingProgress');
    if (!progressBar) return;

    window.addEventListener('scroll', function() {
        var scrollTop = window.scrollY;
        var docHeight = document.documentElement.scrollHeight - window.innerHeight;
        var scrollPercent = docHeight > 0 ? (scrollTop / docHeight) * 100 : 0;
        progressBar.style.width = Math.min(scrollPercent, 100) + '%';
    });
}

function initScrollToTop() {
    var btn = document.getElementById('scrollToTop');
    if (!btn) return;

    window.addEventListener('scroll', function() {
        if (window.scrollY > 400) {
            btn.classList.add('visible');
        } else {
            btn.classList.remove('visible');
        }
    });

    btn.addEventListener('click', function() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });
}

function initTocToggle() {
    var toggle = document.getElementById('tocToggle');
    var sidebar = document.getElementById('tocSidebar');
    if (!toggle || !sidebar) return;

    toggle.addEventListener('click', function() {
        sidebar.classList.toggle('collapsed');
    });
}

/* ===========================
   Copy Code Buttons
   =========================== */

function addCopyButtons() {
    var codeBlocks = document.querySelectorAll('pre code');
    codeBlocks.forEach(function(codeBlock) {
        var pre = codeBlock.parentElement;
        if (pre.querySelector('.copy-code-btn')) return;

        var wrapper = document.createElement('div');
        wrapper.className = 'code-block-wrapper';
        pre.parentNode.insertBefore(wrapper, pre);
        wrapper.appendChild(pre);

        var btn = document.createElement('button');
        btn.className = 'copy-code-btn';
        btn.textContent = 'Sao chép';
        btn.addEventListener('click', function() {
            var text = codeBlock.textContent;
            navigator.clipboard.writeText(text).then(function() {
                btn.textContent = 'Đã sao chép!';
                btn.classList.add('copied');
                setTimeout(function() {
                    btn.textContent = 'Sao chép';
                    btn.classList.remove('copied');
                }, 2000);
            });
        });
        wrapper.appendChild(btn);
    });
}

/* ===========================
   Quiz Page
   =========================== */

var QUIZ_DISPLAY_COUNT = 20;

var quizState = {
    currentIndex: 0,
    totalQuestions: 0,
    totalPool: 0,
    topicId: '',
    answers: {},
    correctCount: 0,
    answered: {},
    selectedCards: []
};

function shuffleArray(arr) {
    for (var i = arr.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }
    return arr;
}

function selectRandomQuestions() {
    var allCards = document.querySelectorAll('.quiz-question-card');
    var indices = [];
    for (var i = 0; i < allCards.length; i++) indices.push(i);
    shuffleArray(indices);
    var count = Math.min(QUIZ_DISPLAY_COUNT, allCards.length);
    var selected = indices.slice(0, count);
    selected.sort(function(a, b) { return a - b; });

    allCards.forEach(function(card) { card.classList.add('quiz-hidden'); });
    var selectedCards = [];
    for (var i = 0; i < selected.length; i++) {
        selectedCards.push(allCards[selected[i]]);
    }
    if (selectedCards.length > 0) selectedCards[0].classList.remove('quiz-hidden');
    return selectedCards;
}

function initQuizPage(topicId, totalQuestions) {
    quizState.topicId = topicId;
    quizState.totalPool = totalQuestions;
    quizState.currentIndex = 0;
    quizState.answers = {};
    quizState.correctCount = 0;
    quizState.answered = {};

    quizState.selectedCards = selectRandomQuestions();
    quizState.totalQuestions = quizState.selectedCards.length;

    var totalEl = document.getElementById('totalQuestions');
    if (totalEl) totalEl.textContent = quizState.totalQuestions;
    var resultTotal = document.querySelector('.result-score-total');
    if (resultTotal) resultTotal.textContent = '/ ' + quizState.totalQuestions;

    hljs.highlightAll();
    updateQuizProgress();
    updateQuizNav();
}

function selectOption(btn, questionId, optionIndex) {
    if (quizState.answered[questionId]) return;
    quizState.answered[questionId] = true;
    quizState.answers[questionId] = optionIndex;

    var card = document.getElementById('question-' + questionId);
    var correctAnswer = parseInt(card.getAttribute('data-correct'));
    var options = card.querySelectorAll('.quiz-option');

    options.forEach(function(opt) {
        opt.disabled = true;
        opt.classList.add('quiz-option-disabled');
    });

    if (optionIndex === correctAnswer) {
        btn.classList.add('quiz-option-correct');
        quizState.correctCount++;
    } else {
        btn.classList.add('quiz-option-wrong');
        options[correctAnswer].classList.add('quiz-option-correct');
    }

    var explanation = document.getElementById('explanation-' + questionId);
    if (explanation) {
        explanation.style.display = 'block';
    }

    updateQuizScore();
    saveQuizProgress();
}

function updateQuizProgress() {
    var current = document.getElementById('currentQuestion');
    var fill = document.getElementById('quizProgressFill');
    if (current) current.textContent = quizState.currentIndex + 1;
    if (fill) {
        var pct = quizState.totalQuestions > 0 ? ((quizState.currentIndex + 1) / quizState.totalQuestions * 100) : 0;
        fill.style.width = pct + '%';
    }
}

function updateQuizScore() {
    var scoreEl = document.getElementById('quizScore');
    if (scoreEl) {
        var answeredCount = Object.keys(quizState.answered).length;
        scoreEl.textContent = 'Đúng: ' + quizState.correctCount + '/' + answeredCount;
    }
}

function updateQuizNav() {
    var prevBtn = document.getElementById('prevBtn');
    var nextBtn = document.getElementById('nextBtn');
    var finishBtn = document.getElementById('finishBtn');

    if (prevBtn) prevBtn.disabled = quizState.currentIndex === 0;

    if (quizState.currentIndex >= quizState.totalQuestions - 1) {
        if (nextBtn) nextBtn.style.display = 'none';
        if (finishBtn) finishBtn.style.display = 'inline-flex';
    } else {
        if (nextBtn) nextBtn.style.display = 'inline-flex';
        if (finishBtn) finishBtn.style.display = 'none';
    }
}

function showQuestion(index) {
    quizState.selectedCards.forEach(function(card, i) {
        if (i === index) {
            card.classList.remove('quiz-hidden');
        } else {
            card.classList.add('quiz-hidden');
        }
    });
    quizState.currentIndex = index;
    updateQuizProgress();
    updateQuizNav();
}

function nextQuestion() {
    if (quizState.currentIndex < quizState.totalQuestions - 1) {
        showQuestion(quizState.currentIndex + 1);
    }
}

function prevQuestion() {
    if (quizState.currentIndex > 0) {
        showQuestion(quizState.currentIndex - 1);
    }
}

function finishQuiz() {
    document.getElementById('quizContainer').style.display = 'none';
    document.getElementById('quizNav').style.display = 'none';
    document.getElementById('quizProgress').style.display = 'none';

    var result = document.getElementById('quizResult');
    result.style.display = 'block';

    var scoreValue = document.getElementById('resultScoreValue');
    var resultFill = document.getElementById('resultFill');
    var resultIcon = document.getElementById('resultIcon');
    var resultTitle = document.getElementById('resultTitle');
    var resultMessage = document.getElementById('resultMessage');

    var pct = quizState.totalQuestions > 0 ? (quizState.correctCount / quizState.totalQuestions * 100) : 0;

    scoreValue.textContent = quizState.correctCount;
    resultFill.style.width = pct + '%';

    if (pct >= 80) {
        resultIcon.textContent = '🏆';
        resultTitle.textContent = 'Xuất sắc!';
        resultMessage.textContent = 'Bạn nắm vững kiến thức rất tốt! Hãy tiếp tục với các bài tập thực hành.';
        resultFill.style.background = 'var(--success)';
    } else if (pct >= 60) {
        resultIcon.textContent = '👍';
        resultTitle.textContent = 'Tốt lắm!';
        resultMessage.textContent = 'Bạn đã nắm được phần lớn kiến thức. Hãy ôn lại những phần còn thiếu.';
        resultFill.style.background = 'var(--warning)';
    } else if (pct >= 40) {
        resultIcon.textContent = '📚';
        resultTitle.textContent = 'Cần cố gắng thêm!';
        resultMessage.textContent = 'Hãy xem lại lý thuyết và thử lại quiz.';
        resultFill.style.background = '#f97316';
    } else {
        resultIcon.textContent = '💪';
        resultTitle.textContent = 'Đừng nản chí!';
        resultMessage.textContent = 'Hãy đọc kỹ lý thuyết trước khi thử lại. Mỗi lần thử là một lần học!';
        resultFill.style.background = 'var(--error)';
    }

    saveQuizResult();
}

function restartQuiz() {
    quizState.currentIndex = 0;
    quizState.answers = {};
    quizState.correctCount = 0;
    quizState.answered = {};

    var allCards = document.querySelectorAll('.quiz-question-card');
    allCards.forEach(function(card) {
        var options = card.querySelectorAll('.quiz-option');
        options.forEach(function(opt) {
            opt.disabled = false;
            opt.classList.remove('quiz-option-disabled', 'quiz-option-correct', 'quiz-option-wrong');
        });
        var explanation = card.querySelector('.quiz-explanation');
        if (explanation) explanation.style.display = 'none';
    });

    quizState.selectedCards = selectRandomQuestions();
    quizState.totalQuestions = quizState.selectedCards.length;

    var totalEl = document.getElementById('totalQuestions');
    if (totalEl) totalEl.textContent = quizState.totalQuestions;
    var resultTotal = document.querySelector('.result-score-total');
    if (resultTotal) resultTotal.textContent = '/ ' + quizState.totalQuestions;

    document.getElementById('quizResult').style.display = 'none';
    document.getElementById('quizContainer').style.display = 'block';
    document.getElementById('quizNav').style.display = 'flex';
    document.getElementById('quizProgress').style.display = 'block';

    showQuestion(0);
    updateQuizScore();

    localStorage.removeItem('quiz_progress_' + quizState.topicId);
}

function reviewQuiz() {
    document.getElementById('quizResult').style.display = 'none';
    document.getElementById('quizContainer').style.display = 'block';
    document.getElementById('quizNav').style.display = 'flex';
    document.getElementById('quizProgress').style.display = 'block';

    quizState.selectedCards.forEach(function(card) {
        card.classList.remove('quiz-hidden');
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function saveQuizProgress() {
    var data = {
        answers: quizState.answers,
        correctCount: quizState.correctCount,
        answered: quizState.answered,
        currentIndex: quizState.currentIndex
    };
    localStorage.setItem('quiz_progress_' + quizState.topicId, JSON.stringify(data));
}

function saveQuizResult() {
    var key = 'quiz_result_' + quizState.topicId;
    var prev = JSON.parse(localStorage.getItem(key) || '{}');
    var pct = quizState.totalQuestions > 0 ? Math.round(quizState.correctCount / quizState.totalQuestions * 100) : 0;
    if (!prev.bestScore || pct > prev.bestScore) {
        prev.bestScore = pct;
        prev.bestCorrect = quizState.correctCount;
        prev.total = quizState.totalQuestions;
    }
    prev.lastScore = pct;
    prev.attempts = (prev.attempts || 0) + 1;
    localStorage.setItem(key, JSON.stringify(prev));
}

function getQuizResult(topicId) {
    return JSON.parse(localStorage.getItem('quiz_result_' + topicId) || '{}');
}
