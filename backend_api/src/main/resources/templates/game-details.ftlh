<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>${game.title! "Game"} - GamerBridge</title>
  <link rel="stylesheet" href="${contextPath!/assets/css/style.css}">
  <style>
    body{background:#0d1117;color:#e5e7eb;font-family:Arial,Helvetica,sans-serif;margin:0}
    .wrap{max-width:1000px;margin:28px auto;padding:20px}
    .header{display:flex;gap:20px;align-items:flex-start}
    .cover{width:260px;height:160px;background:#0b1220;border-radius:8px;overflow:hidden}
    .cover img{width:100%;height:100%;object-fit:cover}
    .meta h1{margin:0 0 6px;color:#7fb1ff}
    .meta .sub{color:#9ca3af;margin-bottom:8px}
    .card{background:#0f1724;padding:18px;border-radius:10px;margin-top:18px}
    .btn{display:inline-block;padding:8px 12px;border-radius:8px;background:#2563eb;color:#fff;text-decoration:none}
    .btn-cta{background:#10b981}
    .reviews{margin-top:18px}
    .review{padding:12px;border-radius:8px;background:#0b1220;margin-bottom:10px}
    form.review-form textarea{width:100%;min-height:90px;padding:8px;border-radius:6px;border:1px solid #1f2937;background:#071022;color:#e5e7eb}
    .muted{color:#9ca3af}
  </style>
</head>
<body>
  <main class="wrap">
    <a href="${contextPath!/gamelist}" class="muted" style="text-decoration:none">← Back to games</a>

    <section class="header" aria-labelledby="game-title">
      <div class="cover">
        <img src="${game.image! '/assets/img/placeholder.png'}" alt="${game.title! 'Cover'}">
      </div>
      <div class="meta">
        <h1 id="game-title">${game.title! "Untitled"}</h1>
        <div class="sub">
          <span>Genre: ${game.genre! "—"}</span> •
          <span>Developer:
            <#if game.developer?? && game.developer.id??>
              <a href="${contextPath!}/developers/${game.developer.id}" style="color:#cbd5e1;text-decoration:underline">${game.developer.name! "Unknown"}</a>
            <#else>
              ${game.developer?if_exists?string("Unknown")}
            </#if>
          </span>
          <#if game.releaseDate??> • <span>Released: ${game.releaseDate?string("yyyy-MM-dd")}</span></#if>
        </div>

        <p class="muted" style="margin:6px 0">${game.description?html}</p>

        <div style="margin-top:12px">
          <form method="post" action="${contextPath!/subscribe}" style="display:inline">
            <input type="hidden" name="gameId" value="${game.id!''}">
            <button class="btn" type="submit">Subscribe</button>
          </form>

          <#if game.price??>
            <span style="margin-left:12px" class="muted">Price: ${game.price?string("0.00")}</span>
          </#if>

          <a href="${contextPath!/review/new?gameId=${game.id}}" class="btn btn-cta" style="margin-left:12px">Write review</a>
        </div>
      </div>
    </section>

    <section class="card">
      <h3>Details</h3>
      <dl>
        <dt class="muted">Platforms</dt>
        <dd>${game.platforms?join(", ")! "N/A"}</dd>

        <dt class="muted">Tags</dt>
        <dd>${game.tags?join(", ")! "—"}</dd>

        <dt class="muted">Description</dt>
        <dd>${game.longDescription!game.description! "No further details."}</dd>
      </dl>
    </section>

    <section class="reviews card" aria-labelledby="reviews-heading">
      <h3 id="reviews-heading">Reviews</h3>

      <#if reviews?size gt 0>
        <#list reviews as r>
          <article class="review" aria-label="Review by ${r.authorName! 'Anonymous'}">
            <strong>${r.authorName! "Anonymous"}</strong>
            <div class="muted" style="font-size:.9rem">${r.rating?string("0")} ★ — ${r.createdAt?string("yyyy-MM-dd")}</div>
            <p style="margin-top:8px">${r.text! ""}</p>
          </article>
        </#list>
      <#else>
        <div class="muted">No reviews yet. Be the first to review this game.</div>
      </#if>

      <#if user??>
        <form class="review-form" id="reviewForm" method="post">
          <label for="reviewText" class="muted">Your review</label>
          <textarea id="reviewText" name="text" placeholder="Share your thoughts..."></textarea>
          <div style="margin-top:8px;display:flex;gap:8px;align-items:center">
            <select name="rating" id="rating">
              <option value="5">5 ★</option>
              <option value="4">4 ★</option>
              <option value="3">3 ★</option>
              <option value="2">2 ★</option>
              <option value="1">1 ★</option>
            </select>
            <button type="button" class="btn btn-cta" onclick="submitReview()">Post review</button>
          </div>
          <div id="revStatus" class="muted" style="margin-top:8px"></div>
        </form>
      <#else>
        <div class="muted">Please <a href="${contextPath!/dlogin}">log in</a> to submit a review.</div>
      </#if>
    </section>
  </main>

  <script>
    function jsonPost(url, payload){
      return fetch(url, {
        method:'POST',
        headers:{'Content-Type':'application/json','Accept':'application/json'},
        body: JSON.stringify(payload)
      }).then(r => r.ok ? r.json() : r.text().then(t=>{throw new Error(t||r.status)}) );
    }

    function submitReview(){
      const txt = document.getElementById('reviewText').value.trim();
      const rating = parseInt(document.getElementById('rating').value,10);
      const status = document.getElementById('revStatus');
      if(!txt){ status.textContent = 'Please enter review text.'; return; }
      status.textContent = 'Posting...';

      // build reviews endpoint using server-rendered values
      const reviewsUrl = '${contextPath!}' + '/api/games/' + '${game.id}' + '/reviews';

      jsonPost(reviewsUrl, { text: txt, rating: rating })
        .then(() => { location.reload(); })
        .catch(e => { console.error(e); status.textContent = 'Failed to post review.'; });
    }
  </script>
</body>
</html>
