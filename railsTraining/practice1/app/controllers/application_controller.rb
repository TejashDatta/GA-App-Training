class ApplicationController < ActionController::Base
  helper_method :current_admin_user
  helper_method :admin_logged_in?

  private

  def current_admin_user
    AdminUser.find_by(id: session[:admin_user_id])
  end

  def admin_logged_in?
    !current_admin_user.nil?
  end

  def authenticate_admin
    return if admin_logged_in?

    redirect_back fallback_location: root_path,
                  alert: t('flash_messages.authenticate_admin_failure')
  end
end
