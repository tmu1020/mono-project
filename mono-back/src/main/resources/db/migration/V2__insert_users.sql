INSERT INTO users (user_id, user_name) VALUES
('u001', '田中 太郎'),
('u002', '佐藤 花子'),
('u003', '山田 次郎'),
('u004', 'Alice Smith'),
('u005', 'Bob Johnson'),
('u006', 'アアア'),
('u007', 'テスト ユーザー'),
('u008', 'user_01'),
('u009', 'user_02'),
('u010', 'aaa'),
('u011', 'zzz'),
('u012', 'あああ'),
('u013', 'いいい')
ON CONFLICT (user_id) DO NOTHING;