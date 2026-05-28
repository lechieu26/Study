var editor;

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
        btn.innerHTML = '✅ Kiểm Tra (Check)';
    });
}

function showResult(result) {
    var panel = document.getElementById('resultPanel');
    var header = document.getElementById('resultHeader');
    var scoreDiv = document.getElementById('resultScore');
    var feedback = document.getElementById('resultFeedback');
    var output = document.getElementById('resultOutput');
    var errorDiv = document.getElementById('resultError');

    panel.style.display = 'block';

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

    if (result.output) {
        output.textContent = result.output;
        output.style.display = 'block';
    } else {
        output.style.display = 'none';
    }

    if (result.error) {
        errorDiv.textContent = result.error;
        errorDiv.style.display = 'block';
    } else {
        errorDiv.style.display = 'none';
    }

    panel.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function toggleSolution() {
    var panel = document.getElementById('solutionPanel');
    var btn = document.getElementById('showSolutionBtn');

    if (panel.style.display === 'none') {
        panel.style.display = 'block';
        btn.innerHTML = '🙈 Ẩn Đáp Án';
        hljs.highlightAll();
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
