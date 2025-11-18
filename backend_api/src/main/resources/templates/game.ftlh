<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Games - GamerBridge</title>
  <link rel="stylesheet" href="${contextPath!/assets/css/style.css}" />
  <style>
    .grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:16px;max-width:1100px;margin:24px auto;padding:0 16px}
    .tile{background:#0f1724;padding:12px;border-radius:10px;color:#e5e7eb}
    .meta{color:#9ca3af;font-size:0.9rem}
    .btn{display:inline-block;margin-top:8px;padding:8px 10px;border-radius:8px;background:#2563eb;color:#fff;text-decoration:none}
  </style>
</head>
<body>
  <main>
    <header style="max-width:1100px;margin:20px auto;padding:0 16px">
      <h1>Games</h1>
      <p style="color:#9ca3af">Browse games and view details.</p>
    </header>

    <section class="grid">
      <#if games?size gt 0>
        <#list games as g>
          <article class="tile">
            <h3>${g.title! "Untitled"}</h3>
            <div class="meta">${g.genre?if_exists?string} • ${g.developer?.name! "Unknown"}</div>
            <p style="margin-top:8px">${g.shortDescription?if_exists?html}</p>
            <a class="btn" href="${contextPath!/game/${g.id}}">View</a>
          </article>
        </#list>
      <#else>
        <div style="max-width:1100px;margin:24px auto;padding:16px;color:#9ca3af">No games found.</div>
      </#if>
    </section>
  </main>
</body>
</html>