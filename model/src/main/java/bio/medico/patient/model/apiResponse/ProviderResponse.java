package bio.medico.patient.model.apiResponse;

import java.util.List;

public class ProviderResponse{
	private List<Items> items;

	public List<Items> getItems(){
		return items;
	}

	public class Items{
		private String phoneNumber;
		private String address;
		private String rev;
		private String icon;
		private String name;
		private String id;
		private String category;
		private String uuid;
		private String email;
		private String createAt;
		private String status;
		private String updatedAt;

		public String getPhoneNumber(){
			return phoneNumber;
		}

		public String getAddress(){
			return address;
		}

		public String getRev(){
			return rev;
		}

		public String getIcon(){
			return icon;
		}

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}

		public String getCategory(){
			return category;
		}

		public String getUuid(){
			return uuid;
		}

		public String getEmail(){
			return email;
		}

		public String getCreateAt(){
			return createAt;
		}

		public String getStatus(){
			return status;
		}

		public String getUpdatedAt(){
			return updatedAt;
		}
	}
}