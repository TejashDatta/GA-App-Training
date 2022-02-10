require 'rails_helper'

RSpec.describe 'AdminAuthentication', type: :request do
  let(:admin_user) { create(:admin_user) }
  let(:admin_login_params) do
    { admin_user: { username: admin_user.username, password: admin_user.password } }
  end

  describe 'GET /admin-login' do
    it 'renders a successful response' do
      get admin_login_path
      expect(response).to be_successful
    end
  end

  describe 'POST /admin-login' do
    context 'when login is valid' do
      it 'sets admin user id in session' do
        post admin_login_path, params: admin_login_params
        expect(session[:admin_user_id]).to eq(admin_user.id)
      end

      it 'redirects to root' do
        post admin_login_path, params: admin_login_params
        expect(response).to redirect_to(root_path)
      end
    end

    context 'when login is invalid' do
      context 'when password is wrong' do
        it "renders a successful response (i.e. to display the 'new' login template)" do
          post admin_login_path,
               params: { admin_user: { username: admin_user.username, password: 'wrong password' } }
          expect(response).to be_successful
        end
      end

      context "when user with username doesn't exist" do
        it "renders a successful response (i.e. to display the 'new' login template)" do
          post admin_login_path,
               params: { admin_user: { username: 'non-existing', password: 'wrong password' } }
          expect(response).to be_successful
        end
      end
    end
  end

  describe 'DELETE /admin-logout' do
    before do
      post admin_login_path, params: admin_login_params
    end

    it 'unsets user id in session' do
      delete admin_logout_path
      expect(session[:admin_user_id]).to be_nil
    end

    it 'redirects to root' do
      delete admin_logout_path
      expect(response).to redirect_to(root_path)
    end
  end
end
