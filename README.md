# Digital Carbon Footprint Validator

[![CI](https://github.com/chiragguruswamy673/carbon-validator/actions/workflows/ci.yml/badge.svg)](https://github.com/chiragguruswamy673/carbon-validator/actions/workflows/ci.yml)

## 🌍 Project Overview
The **Digital Carbon Footprint Validator** is a QA automation framework that measures the environmental impact of websites.  
It calculates:
- **Page size (KB)**
- **Load time (ms)**
- **Estimated CO₂ emissions per visit (g)**

It validates these metrics against configurable thresholds, producing JSON/HTML reports and uploading them as CI/CD artifacts.

---

## ✨ Features
- **Multi‑site testing**: Runs against multiple domains (Demoblaze, BBC, NDTV).
- **Custom JSON/HTML reports**: Recruiter‑friendly artifacts with sustainability metrics.
- **Configurable thresholds**: Controlled via `config.json`.
- **CI/CD integration**: Automated daily runs with GitHub Actions.
- **README badge**: Shows live CI status.

---

## 🛠 Tech Stack
- Java, Selenium, TestNG
- WebDriverManager
- GitHub Actions (CI/CD)

---

## 📊 Example Output
Site=Demoblaze Host=demoblaze.com Size=680.63KB Load=12ms CO2=0.1361g Threshold=2.00g Site=BBC Host=bbc.com Size=1717.84KB Load=26ms CO2=0.3436g Threshold=3.00g Site=NDTV Host=ndtv.com Size=0.67KB Load=31ms CO2=0.0001g Threshold=2.50g


---

## 📂 Artifacts
- **Surefire reports** → Raw TestNG/Maven logs.
- **Carbon reports** → JSON/HTML per site with metrics.
- **(Optional) Dashboard** → Summary index.html with pass/fail chips.

---

## 🌱 Sustainability Context
- **Demoblaze** → Demo e‑commerce site (for testing only).
- **BBC** → Strong sustainability commitments, aiming for net zero by 2030.
- **NDTV** → Covers sustainability topics, lighter footprint in this test.

👉 Among these, **BBC.com** is the most sustainability‑aligned site, with clear corporate goals.

---


Author:
Chirag Guruswamy