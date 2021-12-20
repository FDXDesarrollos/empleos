$(function() {
	$("#copyright-year").text((new Date).getFullYear());
});

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
});

function message(msg){
	swalWithBootstrapButtons.fire(msg);
}

function messageSuc(msg){
	swalWithBootstrapButtons.fire(
	  'Exito!', msg, 'success'
	)	
}

function messageWar(msg){
	swalWithBootstrapButtons.fire(
	  'Atención!', msg, 'warning'
	)	
}

function messageErr(msg){
	swalWithBootstrapButtons.fire(
		'Error!', msg, 'error'
	);
}

function eliminar(url){	
	Swal.fire({
        title: "Spring Jobs",
        text: "¿Eliminar registro?",
        icon: 'warning',
        showCancelButton: true,
		confirmButtonColor: '#d33',
		cancelButtonColor: '#3085d6',			        
        confirmButtonText: "Aceptar",
        cancelButtonText: "Cancelar",
		showLoaderOnConfirm: true,
		preConfirm: function() {
			return new Promise(function(resolve){
				$.ajax({
					url: url,
					type: 'GET',
					dataType: 'json'
					//data: {id: id}
				})
				.done(function(response){
					if (response.value){
						swalWithBootstrapButtons.fire('Exito!', 'Registro eliminado correctamente.', 'success')
						.then((ok)=>{
		    				if(ok){
		    					location.href="/";
		    				}
		    			});	
					}
					else{
					    messageErr("No fue posible eliminar el registro.");
					}
				})
				.fail(function(){
					messageErr("Algo salió mal!");
				});
			});
		},
		allowOutsideClick: false 
	});
}