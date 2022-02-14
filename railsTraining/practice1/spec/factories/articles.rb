FactoryBot.define do
  factory :article do
    sequence(:title) { |index| "title-#{index}" }
    content { 'content' }
  end
end
