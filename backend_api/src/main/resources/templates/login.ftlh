<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Login - GamerBridge</title>
  <link rel="stylesheet" href="${contextPath!/assets/css/style.css}" />
  <style>
    /* minimal local styles for template preview */
    .card{max-width:480px;margin:40px auto;padding:24px;background:#0f1724;border-radius:12px;color:#e5e7eb}
    .form-row{margin-bottom:12px}
    label{display:block;margin-bottom:6px;color:#cbd5e1}
    input,select{width:100%;padding:10px;border-radius:8px;border:1px solid #1f2937;background:#071022;color:#e5e7eb}
    .btn{display:inline-block;padding:10px 14px;border-radius:8px;text-decoration:none;color:#fff;background:#2563eb;border:none;cursor:pointer}
    .btn-outline{background:transparent;border:1px solid rgba(255,255,255,0.06);color:#cbd5e1}
    .dev{background:#10b981}
  </style>
</head>
<body>
  <header>
    <nav><a href="${contextPath!/}">Home</a> | <a href="${contextPath!/}gamelist">Games</a> | <a href="${contextPath!/}about">About</a></nav>
  </header>

  <main>
    <section class="card" role="main" aria-labelledby="loginTitle">
      <h1 id="loginTitle">GamerBridge — Sign in</h1>

      <form method="post" action="${contextPath!/login}">
        <div class="form-row">
          <label for="email">Email</label>
          <input id="email" name="email" type="email" required />
        </div>

        <div class="form-row">
          <label for="password">Password</label>
          <input id="password" name="password" type="password" required />
        </div>

        <div class="form-row">
          <label for="dob">Date of birth</label>
          <input id="dob" name="dob" type="date" />
        </div>

        <div class="form-row">
          <label for="genre">Favorite genre</label>
          <select id="genre" name="genre">
            <option value="">Choose…</option>
            <option>Action</option>
            <option>RPG</option>
            <option>Strategy</option>
            <option>Adventure</option>
            <option>Indie</option>
            <option>Simulation</option>
          </select>
        </div>

        <div style="display:flex;gap:10px;align-items:center;">
          <button type="submit" class="btn">Sign in</button>
          <a class="btn-outline" href="${contextPath!/signup}">Create account</a>
        </div>
      </form>

      <hr style="margin:16px 0;border:none;border-top:1px solid rgba(255,255,255,0.04)">

      <div style="text-align:center">
        <p style="color:#9ca3af;margin:8px 0">Are you a developer?</p>
        <a class="btn dev" href="${contextPath!/dlogin}">Developer Login</a>
        <a class="btn-outline" href="${contextPath!/dsignup}" style="margin-left:8px">Developer Sign up</a>
      </div>
    </section>
  </main>
</body>
</html>