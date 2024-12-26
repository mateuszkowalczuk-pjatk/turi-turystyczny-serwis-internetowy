import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import TouristicPlaceGuaranteedServicesList from '../TouristicPlaceGuaranteedServicesList'
import TouristicPlaceGuaranteedServicesListAdd from '../TouristicPlaceGuaranteedServicesListAdd'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import styles from './TouristicPlaceGuaranteedServices.module.css'

const TouristicPlaceGuaranteedServices = ({ touristicPlaceId }: { touristicPlaceId?: number }) => {
    const { t } = useTranslation()
    const [guaranteedServices, setGuaranteedServices] = useState<GuaranteedService[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const guaranteedServicesResponse = await touristicPlaceService.getAllGuaranteedServices()
            if (guaranteedServicesResponse.status === 200) {
                const guaranteedServicesData: GuaranteedService[] = await guaranteedServicesResponse.json()
                setGuaranteedServices(guaranteedServicesData)
            }
        }
        fetchData().catch((error) => error)
    }, [])

    return (
        <div className={styles.services}>
            <PersonalLabel text={t('tourism.touristic-place-guaranteed-services')} />
            <TouristicPlaceGuaranteedServicesList
                guaranteedServices={guaranteedServices}
                removeGuaranteedServices={(guaranteedServiceId: number) =>
                    setGuaranteedServices((prevServices) =>
                        prevServices.filter((service) => service.guaranteedServiceId !== guaranteedServiceId)
                    )
                }
            />
            {touristicPlaceId && (
                <TouristicPlaceGuaranteedServicesListAdd
                    touristicPlaceId={touristicPlaceId}
                    addGuaranteedServices={(guaranteedService: GuaranteedService) =>
                        setGuaranteedServices((prevServices) => [...prevServices, guaranteedService])
                    }
                />
            )}
        </div>
    )
}

export default TouristicPlaceGuaranteedServices
