# HTML - Dap An Bai Tap

## Bai 1: Tao trang ca nhan

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Trang ca nhan cua Nguyen Van A - Web Developer" />
    <title>Nguyen Van A - Portfolio</title>
</head>
<body>
    <header>
        <h1>Nguyen Van A</h1>
        <p>Full-Stack Web Developer</p>
    </header>

    <main>
        <section>
            <h2>Gioi thieu</h2>
            <img src="https://via.placeholder.com/200" alt="Anh dai dien Nguyen Van A" width="200" height="200" />
            <p>Xin chao! Toi la Nguyen Van A, mot lap trinh vien web voi 3 nam kinh nghiem.
               Toi dam me xay dung cac ung dung web hien dai va than thien voi nguoi dung.</p>
        </section>

        <section>
            <h2>Ky nang</h2>
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
            <h2>Hoc van</h2>
            <table>
                <thead>
                    <tr>
                        <th>Truong</th>
                        <th>Nganh</th>
                        <th>Nam</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>DH Bach Khoa TP.HCM</td>
                        <td>Khoa hoc May tinh</td>
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
            <h2>Lien he</h2>
            <ul>
                <li><a href="https://github.com/nguyenvana" target="_blank" rel="noopener noreferrer">GitHub</a></li>
                <li><a href="https://linkedin.com/in/nguyenvana" target="_blank" rel="noopener noreferrer">LinkedIn</a></li>
                <li><a href="mailto:nguyenvana@email.com">Email</a></li>
            </ul>
        </section>
    </main>

    <footer>
        <p>&copy; 2024 Nguyen Van A. All rights reserved.</p>
    </footer>
</body>
</html>
```

**Giai thich:**
- Su dung cau truc HTML5 chuan voi DOCTYPE, meta charset, viewport
- Semantic tags: header, main, section, footer
- Anh co alt text mo ta
- Bang hoc van voi thead/tbody
- Lien ket external co `target="_blank"` va `rel="noopener noreferrer"` de bao mat

---

## Bai 2: Bieu mau dang ky

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dang ky tai khoan</title>
</head>
<body>
    <main>
        <h1>Dang ky tai khoan</h1>
        <form action="/register" method="POST" enctype="multipart/form-data" novalidate>

            <fieldset>
                <legend>Thong tin ca nhan</legend>

                <label for="fullname">Ho va ten: *</label>
                <input type="text" id="fullname" name="fullname"
                       required minlength="2" maxlength="100"
                       placeholder="Nguyen Van A" />

                <label for="email">Email: *</label>
                <input type="email" id="email" name="email"
                       required placeholder="example@email.com" />

                <label for="password">Mat khau: *</label>
                <input type="password" id="password" name="password"
                       required minlength="8"
                       pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                       title="Toi thieu 8 ky tu, gom chu hoa, chu thuong va so" />

                <label for="confirm-password">Xac nhan mat khau: *</label>
                <input type="password" id="confirm-password" name="confirm_password" required />

                <label for="birthday">Ngay sinh:</label>
                <input type="date" id="birthday" name="birthday"
                       min="1900-01-01" max="2010-12-31" />
            </fieldset>

            <fieldset>
                <legend>Gioi tinh</legend>
                <input type="radio" id="male" name="gender" value="male" />
                <label for="male">Nam</label>

                <input type="radio" id="female" name="gender" value="female" />
                <label for="female">Nu</label>

                <input type="radio" id="other" name="gender" value="other" />
                <label for="other">Khac</label>
            </fieldset>

            <fieldset>
                <legend>Dia chi</legend>
                <label for="city">Thanh pho:</label>
                <select id="city" name="city">
                    <option value="">-- Chon thanh pho --</option>
                    <optgroup label="Mien Bac">
                        <option value="hanoi">Ha Noi</option>
                        <option value="haiphong">Hai Phong</option>
                        <option value="quangninh">Quang Ninh</option>
                    </optgroup>
                    <optgroup label="Mien Trung">
                        <option value="danang">Da Nang</option>
                        <option value="hue">Hue</option>
                    </optgroup>
                    <optgroup label="Mien Nam">
                        <option value="hcm">TP. Ho Chi Minh</option>
                        <option value="cantho">Can Tho</option>
                    </optgroup>
                </select>
            </fieldset>

            <fieldset>
                <legend>So thich</legend>
                <input type="checkbox" id="coding" name="hobbies" value="coding" />
                <label for="coding">Lap trinh</label>

                <input type="checkbox" id="reading" name="hobbies" value="reading" />
                <label for="reading">Doc sach</label>

                <input type="checkbox" id="gaming" name="hobbies" value="gaming" />
                <label for="gaming">Choi game</label>

                <input type="checkbox" id="sports" name="hobbies" value="sports" />
                <label for="sports">The thao</label>
            </fieldset>

            <label for="bio">Gioi thieu ban than:</label>
            <textarea id="bio" name="bio" rows="4" cols="50"
                      maxlength="500" placeholder="Viet vai dong ve ban than..."></textarea>

            <label for="avatar">Anh dai dien:</label>
            <input type="file" id="avatar" name="avatar" accept="image/*" />

            <div>
                <input type="checkbox" id="agree" name="agree" required />
                <label for="agree">Toi dong y voi <a href="/terms">dieu khoan su dung</a> *</label>
            </div>

            <button type="submit">Dang ky</button>
            <button type="reset">Xoa form</button>
        </form>
    </main>
</body>
</html>
```

**Giai thich:**
- Moi input deu co `<label>` voi `for` khop `id`
- Su dung `fieldset` va `legend` nhom cac truong lien quan
- HTML5 validation: `required`, `minlength`, `pattern`, `accept`
- `optgroup` nhom cac thanh pho theo vung mien
- `enctype="multipart/form-data"` can thiet cho file upload

---

## Bai 3: Semantic Blog Layout

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Blog cong nghe - Chia se kien thuc lap trinh" />
    <title>Tech Blog</title>
</head>
<body>
    <a href="#main-content" class="skip-link">Bo qua menu</a>

    <header>
        <h1>Tech Blog</h1>
        <nav aria-label="Menu chinh">
            <ul>
                <li><a href="/">Trang chu</a></li>
                <li><a href="/tutorials">Huong dan</a></li>
                <li><a href="/tips">Meo hay</a></li>
                <li><a href="/about">Gioi thieu</a></li>
                <li><a href="/contact">Lien he</a></li>
            </ul>
        </nav>
    </header>

    <main id="main-content">
        <article>
            <header>
                <h2>Huong dan HTML5 cho nguoi moi bat dau</h2>
                <p>Dang boi <strong>Nguyen Van A</strong> vao
                   <time datetime="2024-01-15">15 thang 1, 2024</time></p>
            </header>
            <figure>
                <img src="html5.jpg" alt="Logo HTML5" loading="lazy" />
                <figcaption>Hinh 1: Logo HTML5 chinh thuc</figcaption>
            </figure>
            <p>HTML5 la phien ban moi nhat cua ngon ngu danh dau sieu van ban...</p>
            <footer>
                <p>Tags: <a href="/tag/html">HTML</a>, <a href="/tag/web">Web</a></p>
            </footer>
        </article>

        <article>
            <header>
                <h2>CSS Grid vs Flexbox - Khi nao dung gi?</h2>
                <p>Dang boi <strong>Tran Thi B</strong> vao
                   <time datetime="2024-01-10">10 thang 1, 2024</time></p>
            </header>
            <figure>
                <img src="css-layout.jpg" alt="So sanh Grid va Flexbox" loading="lazy" />
                <figcaption>Hinh 2: So sanh hai phuong phap layout</figcaption>
            </figure>
            <p>Flexbox va Grid la hai cong cu layout manh me trong CSS hien dai...</p>
            <footer>
                <p>Tags: <a href="/tag/css">CSS</a>, <a href="/tag/layout">Layout</a></p>
            </footer>
        </article>

        <article>
            <header>
                <h2>JavaScript Async/Await - Hieu sau</h2>
                <p>Dang boi <strong>Le Van C</strong> vao
                   <time datetime="2024-01-05">5 thang 1, 2024</time></p>
            </header>
            <figure>
                <img src="async.jpg" alt="Event Loop JavaScript" loading="lazy" />
                <figcaption>Hinh 3: Mo hinh Event Loop</figcaption>
            </figure>
            <p>Async/Await la cu phap giup viet code bat dong bo de doc hon...</p>
            <footer>
                <p>Tags: <a href="/tag/javascript">JavaScript</a>, <a href="/tag/async">Async</a></p>
            </footer>
        </article>

        <section>
            <h2>Cau hoi thuong gap</h2>
            <details>
                <summary>HTML co phai ngon ngu lap trinh khong?</summary>
                <p>Khong. HTML la ngon ngu danh dau (markup language), khong phai ngon ngu lap trinh.
                   No dinh nghia cau truc noi dung, khong co logic dieu khien hay vong lap.</p>
            </details>
            <details>
                <summary>Hoc HTML mat bao lau?</summary>
                <p>Co ban cua HTML co the hoc trong 1-2 tuan. De thanh thao va hieu sau
                   (semantic, accessibility, SEO) can 1-2 thang thuc hanh.</p>
            </details>
        </section>
    </main>

    <aside>
        <section>
            <h3>Bai viet moi nhat</h3>
            <ul>
                <li><a href="#">React 19 co gi moi?</a></li>
                <li><a href="#">TypeScript 5.4 features</a></li>
                <li><a href="#">Docker cho Frontend Dev</a></li>
            </ul>
        </section>
        <section>
            <h3>Tim kiem</h3>
            <form action="/search" method="GET" role="search">
                <label for="search">Tim kiem bai viet:</label>
                <input type="search" id="search" name="q" placeholder="Nhap tu khoa..." />
                <button type="submit">Tim</button>
            </form>
        </section>
    </aside>

    <footer>
        <p>&copy; 2024 Tech Blog. All rights reserved.</p>
        <nav aria-label="Footer links">
            <ul>
                <li><a href="/privacy">Chinh sach bao mat</a></li>
                <li><a href="/terms">Dieu khoan su dung</a></li>
            </ul>
        </nav>
        <address>
            Lien he: <a href="mailto:contact@techblog.com">contact@techblog.com</a><br />
            123 Nguyen Hue, Quan 1, TP.HCM
        </address>
    </footer>
</body>
</html>
```

---

## Bai 4: Bang gia san pham

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bang gia dich vu</title>
</head>
<body>
    <main>
        <h1>Chon goi dich vu phu hop</h1>
        <table>
            <caption>So sanh cac goi dich vu Cloud Hosting</caption>
            <thead>
                <tr>
                    <th scope="col">Tinh nang</th>
                    <th scope="col">Basic</th>
                    <th scope="col">Pro</th>
                    <th scope="col">Enterprise</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">Gia/thang</th>
                    <td>99.000 VND</td>
                    <td>299.000 VND</td>
                    <td>999.000 VND</td>
                </tr>
                <tr>
                    <th scope="row">Dung luong</th>
                    <td>10 GB SSD</td>
                    <td>50 GB SSD</td>
                    <td>200 GB NVMe</td>
                </tr>
                <tr>
                    <th scope="row">Bandwidth</th>
                    <td>100 GB/thang</td>
                    <td>Khong gioi han</td>
                    <td>Khong gioi han</td>
                </tr>
                <tr>
                    <th scope="row">So nguoi dung</th>
                    <td>1</td>
                    <td>5</td>
                    <td>Khong gioi han</td>
                </tr>
                <tr>
                    <th scope="row" rowspan="2">Ho tro</th>
                    <td rowspan="2">Email</td>
                    <td>Email + Chat</td>
                    <td>24/7 Phone + Chat</td>
                </tr>
                <tr>
                    <td>Phan hoi trong 4h</td>
                    <td>Phan hoi trong 1h</td>
                </tr>
                <tr>
                    <th scope="row">SSL mien phi</th>
                    <td>Co</td>
                    <td>Co</td>
                    <td>Co</td>
                </tr>
                <tr>
                    <th scope="row">Auto Backup</th>
                    <td>Hang tuan</td>
                    <td>Hang ngay</td>
                    <td>Real-time</td>
                </tr>
                <tr>
                    <th scope="row">CDN</th>
                    <td>Khong</td>
                    <td>Co</td>
                    <td>Co (Premium)</td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <th scope="row">Hanh dong</th>
                    <td><a href="/signup?plan=basic">Chon Basic</a></td>
                    <td><a href="/signup?plan=pro">Chon Pro</a></td>
                    <td><a href="/signup?plan=enterprise">Chon Enterprise</a></td>
                </tr>
            </tfoot>
        </table>
    </main>
</body>
</html>
```

---

## Bai 5: Trang Multimedia

```html
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="San pham ABC - Giai phap cong nghe hang dau" />
    <link rel="preload" href="hero-video.mp4" as="video" />
    <title>San pham ABC</title>
</head>
<body>
    <a href="#main" class="skip-link">Bo qua den noi dung chinh</a>

    <header>
        <nav aria-label="Menu chinh">
            <ul>
                <li><a href="#intro">Gioi thieu</a></li>
                <li><a href="#gallery">Thu vien anh</a></li>
                <li><a href="#reviews">Danh gia</a></li>
                <li><a href="#faq">FAQ</a></li>
                <li><a href="#contact">Lien he</a></li>
            </ul>
        </nav>
    </header>

    <main id="main">
        <!-- Video gioi thieu -->
        <section id="intro">
            <h1>San pham ABC</h1>
            <video width="640" height="360" controls poster="poster.jpg" preload="metadata">
                <source src="intro.mp4" type="video/mp4" />
                <source src="intro.webm" type="video/webm" />
                <track src="subtitles_vi.vtt" kind="subtitles" srclang="vi" label="Tieng Viet" default />
                Trinh duyet cua ban khong ho tro video.
            </video>
        </section>

        <!-- YouTube embed -->
        <section>
            <h2>Video huong dan su dung</h2>
            <iframe src="https://www.youtube.com/embed/dQw4w9WgXcQ"
                    width="560" height="315"
                    title="Huong dan su dung san pham ABC"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope"
                    allowfullscreen loading="lazy">
            </iframe>
        </section>

        <!-- Gallery -->
        <section id="gallery">
            <h2>Thu vien hinh anh</h2>
            <picture>
                <source media="(min-width: 1024px)" srcset="product-large.webp" type="image/webp" />
                <source media="(min-width: 768px)" srcset="product-medium.webp" type="image/webp" />
                <img src="product-small.jpg" alt="San pham ABC goc truoc"
                     loading="lazy" width="800" height="600" />
            </picture>
            <figure>
                <img src="product-detail.jpg" alt="Chi tiet san pham" loading="lazy" />
                <figcaption>Chi tiet thiet ke san pham ABC</figcaption>
            </figure>
        </section>

        <!-- Audio review -->
        <section id="reviews">
            <h2>Danh gia tu khach hang</h2>
            <figure>
                <audio controls preload="none">
                    <source src="review.mp3" type="audio/mpeg" />
                    <source src="review.ogg" type="audio/ogg" />
                    Trinh duyet khong ho tro audio.
                </audio>
                <figcaption>Feedback cua anh Nguyen Van A - Khach hang VIP</figcaption>
            </figure>
        </section>

        <!-- Canvas logo -->
        <section>
            <h2>Logo</h2>
            <canvas id="logoCanvas" width="300" height="200" aria-label="Logo san pham ABC">
                Trinh duyet khong ho tro Canvas.
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
            <h2>Cau hoi thuong gap</h2>
            <details>
                <summary>San pham co bao hanh khong?</summary>
                <p>Co, san pham duoc bao hanh 24 thang ke tu ngay mua.</p>
            </details>
            <details>
                <summary>Lam sao de doi tra hang?</summary>
                <p>Ban co the doi tra trong 30 ngay neu san pham con nguyen.</p>
            </details>
            <details>
                <summary>Co giao hang toan quoc khong?</summary>
                <p>Co, chung toi giao hang toan quoc, mien phi voi don tu 500.000 VND.</p>
            </details>
        </section>

        <!-- Form lien he -->
        <section id="contact">
            <h2>Lien he voi chung toi</h2>
            <form action="/contact" method="POST">
                <label for="name">Ho ten: *</label>
                <input type="text" id="name" name="name" required aria-required="true" />

                <label for="email">Email: *</label>
                <input type="email" id="email" name="email" required aria-required="true" />

                <label for="phone">So dien thoai:</label>
                <input type="tel" id="phone" name="phone" pattern="[0-9]{10,11}" />

                <label for="subject">Chu de:</label>
                <select id="subject" name="subject">
                    <option value="">-- Chon chu de --</option>
                    <option value="support">Ho tro ky thuat</option>
                    <option value="sales">Mua hang</option>
                    <option value="feedback">Gop y</option>
                </select>

                <label for="message">Noi dung: *</label>
                <textarea id="message" name="message" rows="5" required
                          aria-required="true"></textarea>

                <button type="submit">Gui lien he</button>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; 2024 San pham ABC. All rights reserved.</p>
    </footer>
</body>
</html>
```
