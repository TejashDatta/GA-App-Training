require 'rails_helper'

RSpec.describe Article, type: :model do
  let(:article) { build(:article) }

  describe 'validations' do
    it 'is valid when all attributes are valid' do
      expect(article).to be_valid
    end

    it 'is invalid when title is empty' do
      article.title = ''
      expect(article).not_to be_valid
    end

    it 'is invalid when title is not unique' do
      create(:article, title: article.title)
      expect(article).not_to be_valid
    end

    it 'is invalid when content is empty' do
      article.content = ''
      expect(article).not_to be_valid
    end
  end
end
