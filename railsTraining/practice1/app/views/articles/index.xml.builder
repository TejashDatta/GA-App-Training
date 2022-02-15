xml.instruct!
xml.rss(version: '2.0') do
  xml.channel do
    xml.title 'Rails News'
    xml.language 'ja'
    xml.link 'http://localhost:3000'

    @articles.each do |article|
      xml.item do
        xml.title article.title
        xml.link article_url(article)
        xml.description truncate(article.content, length: 200)
        xml.pubDate article.created_at.rfc2822
      end
    end
  end
end
