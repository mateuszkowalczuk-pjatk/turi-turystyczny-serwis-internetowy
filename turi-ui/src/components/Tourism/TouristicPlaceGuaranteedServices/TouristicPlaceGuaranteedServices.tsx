import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useEffect, useState } from 'react'
import Label from '../../Shared/Controls/Label'
import TouristicPlaceGuaranteedServicesList from '../TouristicPlaceGuaranteedServicesList'
import TouristicPlaceGuaranteedServicesListAdd from '../TouristicPlaceGuaranteedServicesListAdd'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import styles from './TouristicPlaceGuaranteedServices.module.css'

const TouristicPlaceGuaranteedServices = ({ touristicPlaceId }: { touristicPlaceId?: number }) => {
    const { t } = useHooks()
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
            <Label text={t('tourism.touristic-place-guaranteed-services')} />
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
