class Article < ApplicationRecord
  validates :title, presence: true, uniqueness: true
  validates :content, presence: true
end
