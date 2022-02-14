Rails.application.routes.draw do
  resources :articles
  root 'temporary#home'

  get '/admin-login', to: 'admin_authentication#new', as: :admin_login
  post '/admin-login', to: 'admin_authentication#create'
  delete '/admin-logout', to: 'admin_authentication#destroy', as: :admin_logout
end
