FactoryBot.define do
  factory :user do
    sequence(:user_name) { |index| "user_name-#{index}" }
    password { "password" }
  end
end
