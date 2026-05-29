package com.study.service;

import com.study.model.CodeCheckResult;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SqlExecutionService {

    private static final long TIMEOUT_SECONDS = 10;
    private static final int MAX_ROWS = 100;
    private static final String DB_URL = "jdbc:h2:mem:study_sql;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";

    private volatile boolean initialized = false;

    private synchronized void initializeDatabase() {
        if (initialized) return;
        try (Connection conn = DriverManager.getConnection(DB_URL, "sa", "")) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS phong_ban (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        ten_phong_ban VARCHAR(100) NOT NULL,
                        truong_phong_id INT
                    )
                """);
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS nhan_vien (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        ho_ten VARCHAR(100) NOT NULL,
                        email VARCHAR(255) UNIQUE,
                        phong_ban_id INT REFERENCES phong_ban(id),
                        quan_ly_id INT,
                        luong DECIMAL(12, 2) NOT NULL,
                        ngay_vao_lam DATE NOT NULL,
                        trang_thai BOOLEAN DEFAULT TRUE
                    )
                """);
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS du_an (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        ten_du_an VARCHAR(200) NOT NULL,
                        ngay_bat_dau DATE,
                        ngay_ket_thuc DATE,
                        ngan_sach DECIMAL(15, 2)
                    )
                """);
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS phan_cong (
                        nhan_vien_id INT REFERENCES nhan_vien(id),
                        du_an_id INT REFERENCES du_an(id),
                        vai_tro VARCHAR(50),
                        so_gio INT,
                        PRIMARY KEY (nhan_vien_id, du_an_id)
                    )
                """);

                stmt.execute("DELETE FROM phan_cong");
                stmt.execute("DELETE FROM nhan_vien");
                stmt.execute("DELETE FROM du_an");
                stmt.execute("DELETE FROM phong_ban");

                stmt.execute("""
                    INSERT INTO phong_ban (id, ten_phong_ban, truong_phong_id) VALUES
                    (1, 'Kỹ thuật', NULL),
                    (2, 'Kinh doanh', NULL),
                    (3, 'Nhân sự', NULL),
                    (4, 'Marketing', NULL),
                    (5, 'Tài chính', NULL)
                """);

                stmt.execute("""
                    INSERT INTO nhan_vien (id, ho_ten, email, phong_ban_id, quan_ly_id, luong, ngay_vao_lam, trang_thai) VALUES
                    (1, 'Nguyễn Văn An', 'an@company.com', 1, NULL, 25000000, '2018-03-15', TRUE),
                    (2, 'Trần Thị Bình', 'binh@company.com', 1, 1, 18000000, '2019-07-20', TRUE),
                    (3, 'Lê Văn Cường', 'cuong@company.com', 1, 1, 22000000, '2019-01-10', TRUE),
                    (4, 'Phạm Thị Dung', 'dung@company.com', 2, NULL, 20000000, '2017-11-05', TRUE),
                    (5, 'Hoàng Văn Em', 'em@company.com', 2, 4, 15000000, '2020-06-01', TRUE),
                    (6, 'Ngô Thị Phương', 'phuong@company.com', 2, 4, 17000000, '2020-02-14', TRUE),
                    (7, 'Đỗ Văn Giang', 'giang@company.com', 3, NULL, 19000000, '2018-08-22', TRUE),
                    (8, 'Bùi Thị Hoa', 'hoa@company.com', 3, 7, 14000000, '2021-04-10', TRUE),
                    (9, 'Vũ Văn Ích', 'ich@company.com', 4, NULL, 21000000, '2019-05-30', TRUE),
                    (10, 'Lý Thị Kim', 'kim@company.com', 4, 9, 16000000, '2021-09-15', TRUE),
                    (11, 'Trịnh Văn Long', 'long@company.com', 1, 1, 20000000, '2020-01-08', TRUE),
                    (12, 'Mai Thị Ngọc', 'ngoc@company.com', 5, NULL, 23000000, '2018-06-12', TRUE),
                    (13, 'Cao Văn Phú', 'phu@company.com', 5, 12, 17500000, '2021-11-20', TRUE),
                    (14, 'Đinh Thị Quỳnh', 'quynh@company.com', 2, 4, 13000000, '2022-03-01', TRUE),
                    (15, 'Tạ Văn Sơn', 'son@company.com', NULL, NULL, 12000000, '2023-01-15', FALSE)
                """);

                stmt.execute("""
                    INSERT INTO du_an (id, ten_du_an, ngay_bat_dau, ngay_ket_thuc, ngan_sach) VALUES
                    (1, 'Website Thương mại điện tử', '2023-01-01', '2023-06-30', 500000000),
                    (2, 'App Mobile Banking', '2023-03-15', '2023-12-31', 800000000),
                    (3, 'Hệ thống CRM', '2023-06-01', '2024-03-31', 350000000),
                    (4, 'Data Analytics Platform', '2023-09-01', '2024-06-30', 600000000)
                """);

                stmt.execute("""
                    INSERT INTO phan_cong (nhan_vien_id, du_an_id, vai_tro, so_gio) VALUES
                    (1, 1, 'Tech Lead', 500),
                    (2, 1, 'Backend Developer', 400),
                    (3, 1, 'Frontend Developer', 350),
                    (1, 2, 'Architect', 200),
                    (3, 2, 'Frontend Developer', 450),
                    (11, 2, 'Backend Developer', 380),
                    (4, 3, 'Project Manager', 300),
                    (5, 3, 'Business Analyst', 250),
                    (6, 3, 'Sales Lead', 200),
                    (9, 4, 'Data Engineer', 400),
                    (10, 4, 'Data Analyst', 350),
                    (2, 4, 'Backend Developer', 300),
                    (1, 4, 'Advisor', 100)
                """);

                stmt.execute("UPDATE phong_ban SET truong_phong_id = 1 WHERE id = 1");
                stmt.execute("UPDATE phong_ban SET truong_phong_id = 4 WHERE id = 2");
                stmt.execute("UPDATE phong_ban SET truong_phong_id = 7 WHERE id = 3");
                stmt.execute("UPDATE phong_ban SET truong_phong_id = 9 WHERE id = 4");
                stmt.execute("UPDATE phong_ban SET truong_phong_id = 12 WHERE id = 5");
            }
            initialized = true;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể khởi tạo database mẫu: " + e.getMessage(), e);
        }
    }

    public CodeCheckResult executeSql(String sql) {
        initializeDatabase();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<CodeCheckResult> future = executor.submit(() -> doExecuteSql(sql));
            return future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return new CodeCheckResult(false, "", "Truy vấn chạy quá thời gian (" + TIMEOUT_SECONDS + " giây).", 0, "SQL chạy quá lâu.");
        } catch (Exception e) {
            return new CodeCheckResult(false, "", "Lỗi hệ thống: " + e.getMessage(), 0, "Có lỗi xảy ra.");
        } finally {
            executor.shutdownNow();
        }
    }

    private CodeCheckResult doExecuteSql(String sql) {
        String trimmed = sql.trim();
        if (trimmed.isEmpty()) {
            return new CodeCheckResult(false, "", "Vui lòng nhập câu lệnh SQL.", 0, "");
        }

        String[] statements = splitStatements(trimmed);
        StringBuilder allOutput = new StringBuilder();
        int totalScore = 0;
        int stmtCount = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, "sa", "")) {
            conn.setAutoCommit(false);

            for (String stmtSql : statements) {
                stmtSql = stmtSql.trim();
                if (stmtSql.isEmpty()) continue;

                stmtCount++;
                try (Statement stmt = conn.createStatement()) {
                    boolean hasResultSet = stmt.execute(stmtSql);
                    if (hasResultSet) {
                        try (ResultSet rs = stmt.getResultSet()) {
                            allOutput.append(formatResultSet(rs, stmtSql));
                        }
                        totalScore += 50;
                    } else {
                        int updated = stmt.getUpdateCount();
                        allOutput.append("-- ").append(stmtSql.length() > 60 ? stmtSql.substring(0, 60) + "..." : stmtSql).append("\n");
                        allOutput.append("→ ").append(updated).append(" dòng bị ảnh hưởng.\n\n");
                        totalScore += 40;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    String errorMsg = formatSqlError(e);
                    return new CodeCheckResult(false, allOutput.toString(), errorMsg, Math.min(totalScore / Math.max(stmtCount, 1), 100), "Có lỗi SQL. Kiểm tra lại cú pháp và logic.");
                }
            }

            conn.rollback();

            totalScore = Math.min(calculateScore(trimmed, stmtCount, totalScore), 100);
            String feedback = generateSqlFeedback(totalScore, trimmed);

            return new CodeCheckResult(true, allOutput.toString(), "", totalScore, feedback);

        } catch (SQLException e) {
            return new CodeCheckResult(false, "", "Lỗi kết nối database: " + e.getMessage(), 0, "");
        }
    }

    private String[] splitStatements(String sql) {
        List<String> stmts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inString = false;
        boolean inDollarQuote = false;
        char stringChar = 0;

        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);

            if (inString) {
                current.append(c);
                if (c == stringChar) inString = false;
                continue;
            }

            if (c == '\'' || c == '"') {
                inString = true;
                stringChar = c;
                current.append(c);
                continue;
            }

            if (c == '-' && i + 1 < sql.length() && sql.charAt(i + 1) == '-') {
                int end = sql.indexOf('\n', i);
                if (end == -1) end = sql.length();
                i = end - 1;
                continue;
            }

            if (c == ';' && !inDollarQuote) {
                String stmt = current.toString().trim();
                if (!stmt.isEmpty()) stmts.add(stmt);
                current = new StringBuilder();
                continue;
            }

            current.append(c);
        }

        String last = current.toString().trim();
        if (!last.isEmpty()) stmts.add(last);

        return stmts.toArray(new String[0]);
    }

    private String formatResultSet(ResultSet rs, String stmtSql) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        StringBuilder sb = new StringBuilder();

        sb.append("-- ").append(stmtSql.length() > 60 ? stmtSql.substring(0, 60) + "..." : stmtSql).append("\n");

        List<String[]> rows = new ArrayList<>();
        String[] headers = new String[colCount];
        int[] widths = new int[colCount];

        for (int i = 1; i <= colCount; i++) {
            headers[i - 1] = meta.getColumnLabel(i);
            widths[i - 1] = headers[i - 1].length();
        }

        int rowCount = 0;
        while (rs.next() && rowCount < MAX_ROWS) {
            String[] row = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                String val = rs.getString(i);
                row[i - 1] = val == null ? "NULL" : val;
                widths[i - 1] = Math.max(widths[i - 1], row[i - 1].length());
            }
            rows.add(row);
            rowCount++;
        }

        for (int i = 0; i < colCount; i++) {
            widths[i] = Math.min(widths[i], 30);
        }

        sb.append(formatRow(headers, widths));
        sb.append(formatSeparator(widths));
        for (String[] row : rows) {
            sb.append(formatRow(row, widths));
        }

        sb.append("(").append(rowCount).append(" dòng)\n\n");
        return sb.toString();
    }

    private String formatRow(String[] values, int[] widths) {
        StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < values.length; i++) {
            String val = values[i].length() > widths[i] ? values[i].substring(0, widths[i] - 2) + ".." : values[i];
            sb.append(String.format("%-" + widths[i] + "s", val));
            if (i < values.length - 1) sb.append(" | ");
        }
        return sb.append("\n").toString();
    }

    private String formatSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("-");
        for (int i = 0; i < widths.length; i++) {
            sb.append("-".repeat(widths[i]));
            if (i < widths.length - 1) sb.append("-+-");
        }
        return sb.append("-\n").toString();
    }

    private String formatSqlError(SQLException e) {
        String msg = e.getMessage();
        msg = msg.replaceAll("\\[.*?\\]", "").trim();
        return "Lỗi SQL: " + msg;
    }

    private int calculateScore(String sql, int stmtCount, int baseScore) {
        int score = baseScore / Math.max(stmtCount, 1);
        String upper = sql.toUpperCase();

        if (upper.contains("JOIN")) score += 10;
        if (upper.contains("GROUP BY")) score += 10;
        if (upper.contains("HAVING")) score += 5;
        if (upper.contains("OVER") && upper.contains("PARTITION")) score += 15;
        if (upper.contains("WITH RECURSIVE") || upper.contains("WITH ")) score += 10;
        if (upper.contains("CASE WHEN") || upper.contains("CASE\n")) score += 5;
        if (upper.contains("EXISTS") || upper.contains("NOT IN")) score += 5;
        if (upper.contains("COALESCE") || upper.contains("NULLIF")) score += 5;

        return score;
    }

    private String generateSqlFeedback(int score, String sql) {
        StringBuilder fb = new StringBuilder();

        if (score >= 80) {
            fb.append("Xuất sắc! Truy vấn SQL phức tạp và chính xác. ");
        } else if (score >= 60) {
            fb.append("Tốt! Truy vấn hoạt động đúng. ");
        } else if (score >= 40) {
            fb.append("Khá! Truy vấn cơ bản hoạt động. ");
        } else {
            fb.append("Cần cải thiện. Kiểm tra lại cú pháp SQL. ");
        }

        String upper = sql.toUpperCase();
        if (!upper.contains("JOIN") && upper.contains("SELECT")) {
            fb.append("Gợi ý: Thử sử dụng JOIN để kết hợp dữ liệu từ nhiều bảng. ");
        }
        if (!upper.contains("GROUP BY") && (upper.contains("COUNT") || upper.contains("SUM") || upper.contains("AVG"))) {
            fb.append("Gợi ý: Dùng GROUP BY khi sử dụng hàm tập hợp. ");
        }

        return fb.toString().trim();
    }
}
