db.auth('admin_user', 'admin_password')
db.createUser({
  user: 'order_user',
  pwd: 'order_password',
  roles: [
    {
      role: 'readWrite',
      db: 'order_db',
    },
  ],
});
