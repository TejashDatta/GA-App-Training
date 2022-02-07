require "rails_helper"

RSpec.describe User, type: :model do
  let(:user) { build(:user) }

  describe "validations" do
    it "is valid when all attributes are valid" do
      expect(user).to be_valid
    end

    it "is invalid when user name is empty" do
      user.user_name = ""
      expect(user).not_to be_valid
    end

    it "is invalid when password is empty" do
      user.password = ""
      expect(user).not_to be_valid
    end
  end
end
