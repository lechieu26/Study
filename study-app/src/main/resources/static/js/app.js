var editor;

/* ===========================
   Code Editor & Exercise Page
   =========================== */

function initEditor() {
    var textarea = document.getElementById('codeEditor');
    if (!textarea) return;

    editor = CodeMirror.fromTextArea(textarea, {
        mode: 'text/x-java',
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
        editor.setValue('public class Solution {\n    public static void main(String[] args) {\n        // Viết code của bạn ở đây\n\n    }\n}');
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
