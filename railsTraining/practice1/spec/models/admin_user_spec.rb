require 'rails_helper'

RSpec.describe AdminUser, type: :model do
  let(:admin_user) { build(:admin_user) }

  describe 'validations' do
    it 'is valid when all attributes are valid' do
      expect(admin_user).to be_valid
    end

    it 'is invalid when username is empty' do
      admin_user.username = ''
      expect(admin_user).not_to be_valid
    end

    it 'is invalid when username is not unique' do
      create(:admin_user, username: admin_user.username)
      expect(admin_user).not_to be_valid
    end

    it 'is invalid when password is empty' do
      admin_user.password = ''
      expect(admin_user).not_to be_valid
    end
  end
end
