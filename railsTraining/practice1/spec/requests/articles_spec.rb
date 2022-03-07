require 'rails_helper'

RSpec.describe '/articles', type: :request do
  let(:valid_attributes) { { title: 'title', content: 'content' } }
  let(:invalid_attributes) { { title: '', content: '' } }

  let(:article) { create(:article) }

  describe 'GET /index' do
    it 'renders a successful response' do
      article
      get articles_url
      expect(response).to be_successful
    end
  end

  describe 'GET /show' do
    it 'renders a successful response' do
      get article_url(article)
      expect(response).to be_successful
    end
  end

  describe 'GET /new' do
    it 'renders a successful response' do
      get new_article_url
      expect(response).to be_successful
    end
  end

  describe 'GET /edit' do
    it 'render a successful response' do
      get edit_article_url(article)
      expect(response).to be_successful
    end
  end

  describe 'POST /create' do
    context 'with valid parameters' do
      it 'creates a new Article' do
        expect {
          post articles_url, params: { article: valid_attributes }
        }.to change(Article, :count).by(1)
      end

      it 'redirects to the created article' do
        post articles_url, params: { article: valid_attributes }
        expect(response).to redirect_to(article_url(Article.last))
      end
    end

    context 'with invalid parameters' do
      it 'does not create a new Article' do
        expect {
          post articles_url, params: { article: invalid_attributes }
        }.to change(Article, :count).by(0)
      end

      it 'renders an unprocessable entity response' do
        post articles_url, params: { article: invalid_attributes }
        expect(response).to have_http_status(422)
      end
    end
  end

  describe 'PATCH /update' do
    context 'with valid parameters' do
      let(:new_attributes) { { content: 'new content' } }

      it 'updates the requested article' do
        patch article_url(article), params: { article: new_attributes }
        article.reload
        expect(article.content).to eq 'new content'
      end

      it 'redirects to the article' do
        patch article_url(article), params: { article: new_attributes }
        article.reload
        expect(response).to redirect_to(article_url(article))
      end
    end

    context 'with invalid parameters' do
      it 'renders an unprocessable entity response' do
        patch article_url(article), params: { article: invalid_attributes }
        expect(response).to have_http_status(422)
      end
    end
  end

  describe 'DELETE /destroy' do
    it 'destroys the requested article' do
      article
      expect {
        delete article_url(article)
      }.to change(Article, :count).by(-1)
    end

    it 'redirects to the articles list' do
      delete article_url(article)
      expect(response).to redirect_to(articles_url)
    end
  end
end
