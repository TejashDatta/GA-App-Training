FactoryBot.define do
  factory :admin_user do
    sequence(:username) { |index| "username-#{index}" }
    password { "password" }
  end
end
