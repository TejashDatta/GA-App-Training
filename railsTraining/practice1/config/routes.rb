Rails.application.routes.draw do
  get '/admin-login', to: 'admin_authentication#new', as: :admin_login
  post '/admin-login', to: 'admin_authentication#create'
  delete '/admin-logout', to: 'admin_authentication#destroy', as: :admin_logout

  root 'articles#index'
  resources :articles, path: '/'
end
