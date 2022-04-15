const express = require('express');
const router = express.Router();
const db = require('../DB/db');

router.post('/', function(req, res){
		let file = req.file;
		let target = req.query.target;
		let key = req.query.key;
		let value1 = req.query.value1;
		let value2 = req.query.value2;

		if(!file || !target || !key || !value1 || !value2){
			res.send('plz send require elements');
		}
		let file_url = `/${key}_${file.originalname}`;
		let query = ``;
		let data_array =[];
		switch(target){
			case '부모님':
				query = `UPDATE parentinfo SET child_image=? WHERE id=? AND child_name=?;`
				data_array = [
					file_url,
					key,
					value1
				];
				break;
			case '식단표':
				query = `UPDATE food_list SET food_image=? WHERE school=? AND date=? AND food_image=?;`
				data_array = [
					file_url,
					value1,
					key,
					'/default'
				];
				break;
			case '선생님':
				query = `UPDATE teachertinfo SET teacher_image=? WHERE id=?;`
				data_array = [
					file_url,
					key
				];
				break;
			case '원장님':
				query = `UPDATE presidentinfo SET presi_image=? WHERE id=?;`
				data_array = [
					file_url,
					key
				];
				break;
			case '앨범':
				query = `UPDATE album SET album_image=? WHERE title=? AND school=? AND room=? AND album_image=?;`
				data_array = [
					file_url,
					key,
					value1,
					value2,
					'/default'
				];
				break;
		}
		console.log(query, data_array);
		db.query(query, data_array, function(err, result){
			if(err){
				res.status(400).send(err);
				return;
			}
			res.send({res : true, msg : 'success'});
		})
});
module.exports = router;
