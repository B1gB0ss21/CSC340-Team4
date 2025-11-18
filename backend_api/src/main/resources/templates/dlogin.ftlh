<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Developer Login - GamerBridge</title>
  <link rel="stylesheet" href="${contextPath!/assets/css/style.css}" />
  <style>
    .card{max-width:480px;margin:40px auto;padding:24px;background:#0f1724;border-radius:12px;color:#e5e7eb}
    label{display:block;margin-bottom:6px;color:#cbd5e1}
    input{width:100%;padding:10px;border-radius:8px;background:#071022;color:#e5e7eb;border:1px solid #1f2937}
    .btn{padding:10px 14px;border-radius:8px;background:#10b981;color:#fff;border:none;cursor:pointer}
    .btn-outline{background:transparent;border:1px solid rgba(255,255,255,0.06);color:#cbd5e1}
  </style>
</head>
<body>
  <main>
    <section class="card">
      <h1>Developer Portal — Login</h1>

      <form method="post" action="${contextPath!/dlogin}">
        <div>
          <label for="devEmail">Email</label>
          <input id="devEmail" name="email" type="email" required />
        </div>

        <div style="margin-top:12px">
          <label for="devPass">Password</label>
          <input id="devPass" name="password" type="password" required />
        </div>

        <div style="margin-top:14px;display:flex;gap:8px;align-items:center">
          <button class="btn" type="submit">Sign in</button>
          <a class="btn-outline" href="${contextPath!/dsignup}">Create developer account</a>
        </div>
      </form>

      <p style="color:#9ca3af;margin-top:12px">Need help? Contact support or return to <a href="${contextPath!/}">Home</a>.</p>
    </section>
  </main>
</body>
</html>