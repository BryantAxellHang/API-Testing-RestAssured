# 🧪 Tugas Akhir: Automation Testing Saucedemo.com

Proyek ini merupakan tugas akhir Automation Testing yang menggunakan kombinasi **Selenium WebDriver**, **Cucumber**, dan **Page Object Model (POM)** untuk menguji situs web [Saucedemo.com](https://www.saucedemo.com/).

## 📚 Teknologi yang Digunakan

- Java 17+
- Maven
- Selenium WebDriver
- Cucumber (BDD)
- TestNG
- Page Object Model (POM)
- ChromeDriver (Headless/Non-headless)

## 🧩 Struktur Proyek

src/
├── main/
│ └── java/
│ └── pages/ # Kumpulan Page Object (class)
│ └── SauceDemoPage.java
├── test/
│ ├── java/
│ │ ├── runner/ # Runner class untuk Cucumber
│ │ │ └── TestRunner.java
│ │ └── stepdefinitions/ # Step Definitions
│ │ └── StepDefinitionsSln.java
│ └── resources/
│ └── features/ # Feature files Gherkin
│ └── loginselenium.feature

## ✅ Fitur yang Diuji

Berikut skenario yang sudah diimplementasikan dan diuji:

- 🔐 Login:

  - Login berhasil dengan kredensial valid
  - Login gagal dengan kredensial tidak valid

- 🛒 Checkout:

  - Menambahkan produk ke keranjang
  - Checkout berhasil dengan data lengkap
  - Validasi error saat field kosong (first name / postal code)

- 🔁 Logout:

  - Logout dari halaman inventori

- ❌ Remove Product:
  - Menghapus produk dari keranjang
