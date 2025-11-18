<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>${game.title?if_exists?string("Game") } - GamerBridge</title>
  <link rel="stylesheet" href="${contextPath!/assets/css/style.css}" />
  <style>
    .container{max-width:900px;margin:28px auto;padding:0 16px;color:#e5e7eb}
    .card{background:#0f1724;padding:18px;border-radius:10px}
    .meta{color:#9ca3af;margin-bottom:12px}
    .reviews{margin-top:14px}
    .review{padding:10px;border-top:1px solid rgba(255,255,255,0.03)}
    .btn{padding:8px 12px;border-radius:8px;background:#2563eb;color:#fff;text-decoration:none}
  </style>
</head>
<body>
  <main class="container">
    <a href="${contextPath!/gamelist}" class="btn" style="margin-bottom:12px;display:inline-block">← Back to games</a>

    <div class="card">
      <h1>${game.title! "Untitled"}</h1>
      <div class="meta">
        <span>Genre: ${game.genre! "—"}</span> •
        <span>Developer: ${game.developer?.name! "Unknown"}</span>
      </div>

      <p>${game.description! "No description available."}</p>

      <div style="margin-top:14px">
        <form method="post" action="${contextPath!/subscribe}">
          <input type="hidden" name="gameId" value="${game.id}" />
          <button class="btn" type="submit">Subscribe / Follow</button>
        </form>
        <a href="${contextPath!/review/new?gameId=${game.id}}" class="btn" style="margin-left:8px;background:#10b981">Write review</a>
      </div>

      <section class="reviews">
        <h3>Reviews</h3>
        <#if reviews?size gt 0>
          <#list reviews as r>
            <div class="review">
              <strong>${r.authorName! "Anonymous"}</strong>
              <div class="meta">${r.rating?string} ★ — <span>${r.createdAt?string("yyyy-MM-dd")}</span></div>
              <div>${r.text! ""}</div>
            </div>
          </#list>
        <#else>
          <div class="review">No reviews yet. Be the first to review this game.</div>
        </#if>
      </section>
    </div>
  </main>
</body>
</html>