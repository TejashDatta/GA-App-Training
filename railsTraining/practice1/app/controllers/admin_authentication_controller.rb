class AdminAuthenticationController < ApplicationController
  def new
    @admin_user = AdminUser.new
  end

  def create
    @admin_user = AdminUser.find_by(username: admin_user_params[:username])
    if @admin_user && @admin_user.password == admin_user_params[:password]
      session[:admin_user_id] = @admin_user.id
      redirect_to root_path
    else
      @admin_user = AdminUser.new(admin_user_params)
      flash.alert = t('flash_messages.login_failure')
      render :new
    end
  end

  def destroy
    session[:admin_user_id] = nil
    redirect_to root_path, notice: t('flash_messages.logout_success')
  end

  private

  def admin_user_params
    params.require(:admin_user).permit(:username, :password)
  end
end
