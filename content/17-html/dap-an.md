# HTML - Đáp Án Bài Tập

## Bài 1: Tạo trang cá nhân

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Trang cá nhân của Nguyễn Văn A - Web Developer" />
    <title>Nguyễn Văn A - Portfolio</title>
</head>
<body>
    <header>
        <h1>Nguyễn Văn A</h1>
        <p>Full-Stack Web Developer</p>
    </header>

    <main>
        <section>
            <h2>Giới thiệu</h2>
            <img src="https://via.placeholder.com/200" alt="Ảnh đại diện Nguyễn Văn A" width="200" height="200" />
            <p>Xin chào! Tôi là Nguyễn Văn A, một lập trình viên web với 3 năm kinh nghiệm.
               Tôi đam mê xây dựng các ứng dụng web hiện đại và thân thiện với người dùng.</p>
        </section>

        <section>
            <h2>Kỹ năng</h2>
            <ul>
                <li>HTML5 &amp; CSS3</li>
                <li>JavaScript (ES6+)</li>
                <li>React &amp; Next.js</li>
                <li>Node.js &amp; Express</li>
                <li>Java &amp; Spring Boot</li>
                <li>PostgreSQL &amp; MongoDB</li>
            </ul>
        </section>

        <section>
            <h2>Học vấn</h2>
            <table>
                <thead>
                    <tr>
                        <th>Trường</th>
                        <th>Ngành</th>
                        <th>Năm</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>ĐH Bách Khoa TP.HCM</td>
                        <td>Khoa học Máy tính</td>
                        <td>2018 - 2022</td>
                    </tr>
                    <tr>
                        <td>FPT Software Academy</td>
                        <td>Full-Stack Development</td>
                        <td>2022</td>
                    </tr>
                </tbody>
            </table>
        </section>

        <section>
            <h2>Liên hệ</h2>
            <ul>
                <li><a href="https://github.com/nguyenvana" target="_blank" rel="noopener noreferrer">GitHub</a></li>
                <li><a href="https://linkedin.com/in/nguyenvana" target="_blank" rel="noopener noreferrer">LinkedIn</a></li>
                <li><a href="mailto:nguyenvana@email.com">Email</a></li>
            </ul>
        </section>
    </main>

    <footer>
        <p>&copy; 2024 Nguyễn Văn A. All rights reserved.</p>
    </footer>
</body>
</html>
```

**Giải thích:**
- Sử dụng cấu trúc HTML5 chuẩn với DOCTYPE, meta charset, viewport
- Semantic tags: header, main, section, footer
- Ảnh có alt text mô tả
- Bảng học vấn với thead/tbody
- Liên kết external có `target="_blank"` và `rel="noopener noreferrer"` để bảo mật

---

## Bài 2: Biểu mẫu đăng ký

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng ký tài khoản</title>
</head>
<body>
    <main>
        <h1>Đăng ký tài khoản</h1>
        <form action="/register" method="POST" enctype="multipart/form-data" novalidate>

            <fieldset>
                <legend>Thông tin cá nhân</legend>

                <label for="fullname">Họ và tên: *</label>
                <input type="text" id="fullname" name="fullname"
                       required minlength="2" maxlength="100"
                       placeholder="Nguyễn Văn A" />

                <label for="email">Email: *</label>
                <input type="email" id="email" name="email"
                       required placeholder="example@email.com" />

                <label for="password">Mật khẩu: *</label>
                <input type="password" id="password" name="password"
                       required minlength="8"
                       pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                       title="Tối thiểu 8 ký tự, gồm chữ hoa, chữ thường và số" />

                <label for="confirm-password">Xác nhận mật khẩu: *</label>
                <input type="password" id="confirm-password" name="confirm_password" required />

                <label for="birthday">Ngày sinh:</label>
                <input type="date" id="birthday" name="birthday"
                       min="1900-01-01" max="2010-12-31" />
            </fieldset>

            <fieldset>
                <legend>Giới tính</legend>
                <input type="radio" id="male" name="gender" value="male" />
                <label for="male">Nam</label>

                <input type="radio" id="female" name="gender" value="female" />
                <label for="female">Nữ</label>

                <input type="radio" id="other" name="gender" value="other" />
                <label for="other">Khác</label>
            </fieldset>

            <fieldset>
                <legend>Địa chỉ</legend>
                <label for="city">Thành phố:</label>
                <select id="city" name="city">
                    <option value="">-- Chọn thành phố --</option>
                    <optgroup label="Miền Bắc">
                        <option value="hanoi">Hà Nội</option>
                        <option value="haiphong">Hải Phòng</option>
                        <option value="quangninh">Quảng Ninh</option>
                    </optgroup>
                    <optgroup label="Miền Trung">
                        <option value="danang">Đà Nẵng</option>
                        <option value="hue">Huế</option>
                    </optgroup>
                    <optgroup label="Miền Nam">
                        <option value="hcm">TP. Hồ Chí Minh</option>
                        <option value="cantho">Cần Thơ</option>
                    </optgroup>
                </select>
            </fieldset>

            <fieldset>
                <legend>Sở thích</legend>
                <input type="checkbox" id="coding" name="hobbies" value="coding" />
                <label for="coding">Lập trình</label>

                <input type="checkbox" id="reading" name="hobbies" value="reading" />
                <label for="reading">Đọc sách</label>

                <input type="checkbox" id="gaming" name="hobbies" value="gaming" />
                <label for="gaming">Chơi game</label>

                <input type="checkbox" id="sports" name="hobbies" value="sports" />
                <label for="sports">Thể thao</label>
            </fieldset>

            <label for="bio">Giới thiệu bản thân:</label>
            <textarea id="bio" name="bio" rows="4" cols="50"
                      maxlength="500" placeholder="Viết vài dòng về bản thân..."></textarea>

            <label for="avatar">Ảnh đại diện:</label>
            <input type="file" id="avatar" name="avatar" accept="image/*" />

            <div>
                <input type="checkbox" id="agree" name="agree" required />
                <label for="agree">Tôi đồng ý với <a href="/terms">điều khoản sử dụng</a> *</label>
            </div>

            <button type="submit">Đăng ký</button>
            <button type="reset">Xóa form</button>
        </form>
    </main>
</body>
</html>
```

**Giải thích:**
- Mỗi input đều có `<label>` với `for` khớp `id`
- Sử dụng `fieldset` và `legend` nhóm các trường liên quan
- HTML5 validation: `required`, `minlength`, `pattern`, `accept`
- `optgroup` nhóm các thành phố theo vùng miền
- `enctype="multipart/form-data"` cần thiết cho file upload

---

## Bài 3: Semantic Blog Layout

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Blog công nghệ - Chia sẻ kiến thức lập trình" />
    <title>Tech Blog</title>
</head>
<body>
    <a href="#main-content" class="skip-link">Bỏ qua menu</a>

    <header>
        <h1>Tech Blog</h1>
        <nav aria-label="Menu chính">
            <ul>
                <li><a href="/">Trang chủ</a></li>
                <li><a href="/tutorials">Hướng dẫn</a></li>
                <li><a href="/tips">Mẹo hay</a></li>
                <li><a href="/about">Giới thiệu</a></li>
                <li><a href="/contact">Liên hệ</a></li>
            </ul>
        </nav>
    </header>

    <main id="main-content">
        <article>
            <header>
                <h2>Hướng dẫn HTML5 cho người mới bắt đầu</h2>
                <p>Đăng bởi <strong>Nguyễn Văn A</strong> vào
                   <time datetime="2024-01-15">15 tháng 1, 2024</time></p>
            </header>
            <figure>
                <img src="html5.jpg" alt="Logo HTML5" loading="lazy" />
                <figcaption>Hình 1: Logo HTML5 chính thức</figcaption>
            </figure>
            <p>HTML5 là phiên bản mới nhất của ngôn ngữ đánh dấu siêu văn bản...</p>
            <footer>
                <p>Tags: <a href="/tag/html">HTML</a>, <a href="/tag/web">Web</a></p>
            </footer>
        </article>

        <article>
            <header>
                <h2>CSS Grid vs Flexbox - Khi nào dùng gì?</h2>
                <p>Đăng bởi <strong>Trần Thị B</strong> vào
                   <time datetime="2024-01-10">10 tháng 1, 2024</time></p>
            </header>
            <figure>
                <img src="css-layout.jpg" alt="So sánh Grid và Flexbox" loading="lazy" />
                <figcaption>Hình 2: So sánh hai phương pháp layout</figcaption>
            </figure>
            <p>Flexbox và Grid là hai công cụ layout mạnh mẽ trong CSS hiện đại...</p>
            <footer>
                <p>Tags: <a href="/tag/css">CSS</a>, <a href="/tag/layout">Layout</a></p>
            </footer>
        </article>

        <article>
            <header>
                <h2>JavaScript Async/Await - Hiểu sâu</h2>
                <p>Đăng bởi <strong>Lê Văn C</strong> vào
                   <time datetime="2024-01-05">5 tháng 1, 2024</time></p>
            </header>
            <figure>
                <img src="async.jpg" alt="Event Loop JavaScript" loading="lazy" />
                <figcaption>Hình 3: Mô hình Event Loop</figcaption>
            </figure>
            <p>Async/Await là cú pháp giúp viết code bất đồng bộ dễ đọc hơn...</p>
            <footer>
                <p>Tags: <a href="/tag/javascript">JavaScript</a>, <a href="/tag/async">Async</a></p>
            </footer>
        </article>

        <section>
            <h2>Câu hỏi thường gặp</h2>
            <details>
                <summary>HTML có phải ngôn ngữ lập trình không?</summary>
                <p>Không. HTML là ngôn ngữ đánh dấu (markup language), không phải ngôn ngữ lập trình.
                   Nó định nghĩa cấu trúc nội dung, không có logic điều khiển hay vòng lặp.</p>
            </details>
            <details>
                <summary>Học HTML mất bao lâu?</summary>
                <p>Cơ bản của HTML có thể học trong 1-2 tuần. Để thành thạo và hiểu sâu
                   (semantic, accessibility, SEO) cần 1-2 tháng thực hành.</p>
            </details>
        </section>
    </main>

    <aside>
        <section>
            <h3>Bài viết mới nhất</h3>
            <ul>
                <li><a href="#">React 19 có gì mới?</a></li>
                <li><a href="#">TypeScript 5.4 features</a></li>
                <li><a href="#">Docker cho Frontend Dev</a></li>
            </ul>
        </section>
        <section>
            <h3>Tìm kiếm</h3>
            <form action="/search" method="GET" role="search">
                <label for="search">Tìm kiếm bài viết:</label>
                <input type="search" id="search" name="q" placeholder="Nhập từ khóa..." />
                <button type="submit">Tìm</button>
            </form>
        </section>
    </aside>

    <footer>
        <p>&copy; 2024 Tech Blog. All rights reserved.</p>
        <nav aria-label="Footer links">
            <ul>
                <li><a href="/privacy">Chính sách bảo mật</a></li>
                <li><a href="/terms">Điều khoản sử dụng</a></li>
            </ul>
        </nav>
        <address>
            Liên hệ: <a href="mailto:contact@techblog.com">contact@techblog.com</a><br />
            123 Nguyễn Huệ, Quận 1, TP.HCM
        </address>
    </footer>
</body>
</html>
```

---

## Bài 4: Bảng giá sản phẩm

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bảng giá dịch vụ</title>
</head>
<body>
    <main>
        <h1>Chọn gói dịch vụ phù hợp</h1>
        <table>
            <caption>So sánh các gói dịch vụ Cloud Hosting</caption>
            <thead>
                <tr>
                    <th scope="col">Tính năng</th>
                    <th scope="col">Basic</th>
                    <th scope="col">Pro</th>
                    <th scope="col">Enterprise</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">Giá/tháng</th>
                    <td>99.000 VND</td>
                    <td>299.000 VND</td>
                    <td>999.000 VND</td>
                </tr>
                <tr>
                    <th scope="row">Dung lượng</th>
                    <td>10 GB SSD</td>
                    <td>50 GB SSD</td>
                    <td>200 GB NVMe</td>
                </tr>
                <tr>
                    <th scope="row">Bandwidth</th>
                    <td>100 GB/tháng</td>
                    <td>Không giới hạn</td>
                    <td>Không giới hạn</td>
                </tr>
                <tr>
                    <th scope="row">Số người dùng</th>
                    <td>1</td>
                    <td>5</td>
                    <td>Không giới hạn</td>
                </tr>
                <tr>
                    <th scope="row" rowspan="2">Hỗ trợ</th>
                    <td rowspan="2">Email</td>
                    <td>Email + Chat</td>
                    <td>24/7 Phone + Chat</td>
                </tr>
                <tr>
                    <td>Phản hồi trong 4h</td>
                    <td>Phản hồi trong 1h</td>
                </tr>
                <tr>
                    <th scope="row">SSL miễn phí</th>
                    <td>Có</td>
                    <td>Có</td>
                    <td>Có</td>
                </tr>
                <tr>
                    <th scope="row">Auto Backup</th>
                    <td>Hàng tuần</td>
                    <td>Hàng ngày</td>
                    <td>Real-time</td>
                </tr>
                <tr>
                    <th scope="row">CDN</th>
                    <td>Không</td>
                    <td>Có</td>
                    <td>Có (Premium)</td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <th scope="row">Hành động</th>
                    <td><a href="/signup?plan=basic">Chọn Basic</a></td>
                    <td><a href="/signup?plan=pro">Chọn Pro</a></td>
                    <td><a href="/signup?plan=enterprise">Chọn Enterprise</a></td>
                </tr>
            </tfoot>
        </table>
    </main>
</body>
</html>
```

---

## Bài 5: Trang Multimedia

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Sản phẩm ABC - Giải pháp công nghệ hàng đầu" />
    <link rel="preload" href="hero-video.mp4" as="video" />
    <title>Sản phẩm ABC</title>
</head>
<body>
    <a href="#main" class="skip-link">Bỏ qua đến nội dung chính</a>

    <header>
        <nav aria-label="Menu chính">
            <ul>
                <li><a href="#intro">Giới thiệu</a></li>
                <li><a href="#gallery">Thư viện ảnh</a></li>
                <li><a href="#reviews">Đánh giá</a></li>
                <li><a href="#faq">FAQ</a></li>
                <li><a href="#contact">Liên hệ</a></li>
            </ul>
        </nav>
    </header>

    <main id="main">
        <!-- Video giới thiệu -->
        <section id="intro">
            <h1>Sản phẩm ABC</h1>
            <video width="640" height="360" controls poster="poster.jpg" preload="metadata">
                <source src="intro.mp4" type="video/mp4" />
                <source src="intro.webm" type="video/webm" />
                <track src="subtitles_vi.vtt" kind="subtitles" srclang="vi" label="Tiếng Việt" default />
                Trình duyệt của bạn không hỗ trợ video.
            </video>
        </section>

        <!-- YouTube embed -->
        <section>
            <h2>Video hướng dẫn sử dụng</h2>
            <iframe src="https://www.youtube.com/embed/dQw4w9WgXcQ"
                    width="560" height="315"
                    title="Hướng dẫn sử dụng sản phẩm ABC"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope"
                    allowfullscreen loading="lazy">
            </iframe>
        </section>

        <!-- Gallery -->
        <section id="gallery">
            <h2>Thư viện hình ảnh</h2>
            <picture>
                <source media="(min-width: 1024px)" srcset="product-large.webp" type="image/webp" />
                <source media="(min-width: 768px)" srcset="product-medium.webp" type="image/webp" />
                <img src="product-small.jpg" alt="Sản phẩm ABC góc trước"
                     loading="lazy" width="800" height="600" />
            </picture>
            <figure>
                <img src="product-detail.jpg" alt="Chi tiết sản phẩm" loading="lazy" />
                <figcaption>Chi tiết thiết kế sản phẩm ABC</figcaption>
            </figure>
        </section>

        <!-- Audio review -->
        <section id="reviews">
            <h2>Đánh giá từ khách hàng</h2>
            <figure>
                <audio controls preload="none">
                    <source src="review.mp3" type="audio/mpeg" />
                    <source src="review.ogg" type="audio/ogg" />
                    Trình duyệt không hỗ trợ audio.
                </audio>
                <figcaption>Feedback của anh Nguyễn Văn A - Khách hàng VIP</figcaption>
            </figure>
        </section>

        <!-- Canvas logo -->
        <section>
            <h2>Logo</h2>
            <canvas id="logoCanvas" width="300" height="200" aria-label="Logo sản phẩm ABC">
                Trình duyệt không hỗ trợ Canvas.
            </canvas>
            <script>
                const canvas = document.getElementById('logoCanvas');
                const ctx = canvas.getContext('2d');
                ctx.fillStyle = '#3498db';
                ctx.beginPath();
                ctx.arc(150, 80, 50, 0, Math.PI * 2);
                ctx.fill();
                ctx.fillStyle = '#ffffff';
                ctx.font = 'bold 24px Arial';
                ctx.textAlign = 'center';
                ctx.fillText('ABC', 150, 90);
                ctx.fillStyle = '#2c3e50';
                ctx.font = '14px Arial';
                ctx.fillText('Innovation First', 150, 160);
            </script>
        </section>

        <!-- FAQ -->
        <section id="faq">
            <h2>Câu hỏi thường gặp</h2>
            <details>
                <summary>Sản phẩm có bảo hành không?</summary>
                <p>Có, sản phẩm được bảo hành 24 tháng kể từ ngày mua.</p>
            </details>
            <details>
                <summary>Làm sao để đổi trả hàng?</summary>
                <p>Bạn có thể đổi trả trong 30 ngày nếu sản phẩm còn nguyên.</p>
            </details>
            <details>
                <summary>Có giao hàng toàn quốc không?</summary>
                <p>Có, chúng tôi giao hàng toàn quốc, miễn phí với đơn từ 500.000 VND.</p>
            </details>
        </section>

        <!-- Form liên hệ -->
        <section id="contact">
            <h2>Liên hệ với chúng tôi</h2>
            <form action="/contact" method="POST">
                <label for="name">Họ tên: *</label>
                <input type="text" id="name" name="name" required aria-required="true" />

                <label for="email">Email: *</label>
                <input type="email" id="email" name="email" required aria-required="true" />

                <label for="phone">Số điện thoại:</label>
                <input type="tel" id="phone" name="phone" pattern="[0-9]{10,11}" />

                <label for="subject">Chủ đề:</label>
                <select id="subject" name="subject">
                    <option value="">-- Chọn chủ đề --</option>
                    <option value="support">Hỗ trợ kỹ thuật</option>
                    <option value="sales">Mua hàng</option>
                    <option value="feedback">Góp ý</option>
                </select>

                <label for="message">Nội dung: *</label>
                <textarea id="message" name="message" rows="5" required
                          aria-required="true"></textarea>

                <button type="submit">Gửi liên hệ</button>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; 2024 Sản phẩm ABC. All rights reserved.</p>
    </footer>
</body>
</html>
```
